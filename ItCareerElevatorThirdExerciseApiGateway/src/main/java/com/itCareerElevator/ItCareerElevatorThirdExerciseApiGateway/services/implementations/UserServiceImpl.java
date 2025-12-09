package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.implementations;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.authRelated.AuthResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.userRelated.MsvcUpdateUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.userRelated.UpdateUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.userRelated.UserFollowRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.userRelated.MsvcCreateUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.authRelated.AuthRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.userRelated.MsvcFollowUserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.userRelated.UserResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities.User;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.exceptions.InvalidCredentialsException;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.exceptions.UserAlreadyExistsException;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.repositories.UserRepository;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.UserService;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.utils.CustomUserDetails;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final JwtUtil jwtUtil;
    private final WebClient persistenceServiceWebClient;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;

    private final UserRepository userRepository;

    public UserServiceImpl(
            JwtUtil jwtUtil, @Lazy AuthenticationManager authenticationManager,
            PasswordEncoder encoder, UserRepository userRepository,
            WebClient persistenceServiceWebClient
    ) {
        this.jwtUtil = jwtUtil;
        this.persistenceServiceWebClient = persistenceServiceWebClient;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @Override
    public AuthResponseDTO register(AuthRequestDTO userRequest) { // ? Cache the users in Redis? (API gateway has to be as FAST as possible!)
        if (findByUsername(userRequest.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(
                    String.format("User with username: %s already exists.", userRequest.getUsername())
            );
        }

        User user = constructNonPersistedUser(userRequest);
        user = save(user);

        // ! Fire and forget
        log.info("Making a request to the persist microservice.");
        persistenceServiceWebClient
                .post()
                .uri("/api/users")
                .bodyValue(new MsvcCreateUserRequestDTO(
                        user.getSnowflakeId(),
                        userRequest.getUsername()
                ))
                .retrieve()
                .bodyToMono(Void.class) // TODO: Do we need the returned data?
                .subscribe();

        return authenticate(user.getUsername(), userRequest.getPassword());
    }

    private User constructNonPersistedUser(AuthRequestDTO userRequest) {
        String encodedPassword = encoder.encode(userRequest.getPassword());

        return new User(userRequest.getUsername(), encodedPassword);
    }

    @Override
    public User save(User user) {
        log.info("Persisting user with username {} to the Database.", user.getUsername());

        return userRepository.save(user);
    }

    @Override
    public AuthResponseDTO authenticate(String username, String password) {
        try {
            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String jwtToken = jwtUtil.generateToken(userDetails.getUsername());

            return new AuthResponseDTO(userDetails.getUsername(), jwtToken);

        } catch (BadCredentialsException | UsernameNotFoundException ex) {
            throw new InvalidCredentialsException("Invalid username or password.");
        }
    }

    @Override
    public User getCurrentlyLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();

        if (principal instanceof CustomUserDetails cud) {
            return cud.getUser();
        }

        throw new IllegalStateException("No authenticated user."); // Practically this would never happen
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found."));

        return new CustomUserDetails(user);
    }

    @Override
    public UserResponseDTO follow(UserFollowRequestDTO requestDTO) {
        User loggedUser = getCurrentlyLoggedUser();

        log.info("Making a request to the persist microservice.");
        return persistenceServiceWebClient
                .post()
                .uri("/api/users/follow")
                .bodyValue(new MsvcFollowUserRequestDTO(
                        loggedUser.getSnowflakeId(),
                        requestDTO.getUsername()
                ))
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals,
                        resp -> Mono.error(new IllegalArgumentException("Error occurred."))) // TODO: Handle errors
                .bodyToMono(UserResponseDTO.class)
                .block();
    }

    @Override
    public UserResponseDTO unfollow(UserFollowRequestDTO requestDTO) {
        User loggedUser = getCurrentlyLoggedUser();

        log.info("Making a request to the persist microservice.");
        return persistenceServiceWebClient
                .method(HttpMethod.DELETE)
                .uri("/api/users/follow")
                .bodyValue(new MsvcFollowUserRequestDTO(
                        loggedUser.getSnowflakeId(),
                        requestDTO.getUsername()
                ))
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals,
                        resp -> Mono.error(new IllegalArgumentException("Error occurred."))) // TODO: Handle errors
                .bodyToMono(UserResponseDTO.class)
                .block();
    }

    @Override
    public UserResponseDTO update(UpdateUserRequestDTO userRequestDTO) {
        User loggedUser = getCurrentlyLoggedUser();

        log.info("Making a request to the persist microservice.");
        return persistenceServiceWebClient
                .patch()
                .uri("/api/users")
                .bodyValue(new MsvcUpdateUserRequestDTO(
                        loggedUser.getSnowflakeId(),
                        userRequestDTO.getFirstName(),
                        userRequestDTO.getLastName(),
                        userRequestDTO.getIsMale(),
                        userRequestDTO.getBio(),
                        userRequestDTO.getCity(),
                        userRequestDTO.getCountry()
                ))
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals,
                        resp -> Mono.error(new IllegalArgumentException("Error occurred."))) // TODO: Handle errors
                .bodyToMono(UserResponseDTO.class)
                .block();
    }
}
