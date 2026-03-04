package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.auth.request.AssignRolesRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.DepositAmountRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.msvc.MsvcDepositAmountRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.PatchUserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.RegisterRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.AlterUserResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.AuthResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.DepositAmountResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.common.ErrorResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;
import com.ItCareerElevatorSixthExercise.exceptions.auth.InvalidCredentialsException;
import com.ItCareerElevatorSixthExercise.exceptions.auth.NoSuchUserException;
import com.ItCareerElevatorSixthExercise.exceptions.msvc.UserServiceException;
import com.ItCareerElevatorSixthExercise.repositories.UserRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserService;
import com.ItCareerElevatorSixthExercise.utils.auth.CustomUserDetails;
import com.ItCareerElevatorSixthExercise.utils.auth.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.ItCareerElevatorSixthExercise.utils.common.RetryPolicy.buildRetrySpec;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final WebClient userServiceWebClient;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(
            JwtUtils jwtUtils, UserRepository userRepository,
            WebClient userServiceWebClient,
            @Lazy AuthenticationManager authenticationManager

    ) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.userServiceWebClient = userServiceWebClient;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponseDTO register(RegisterRequestDTO userRequest) {
        log.info("---| Making a request to the userService.");

        User user = userServiceWebClient
                .post()
                .uri("/api/users")
                .bodyValue(userRequest)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        res -> res
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(UserServiceException::new)
                                .flatMap(Mono::error))
                .bodyToMono(User.class)
                .retryWhen(buildRetrySpec())
                .block();

        return authenticate(user.getUsername(), userRequest.getPassword());
    }

    @Override
    public AuthResponseDTO authenticate(String username, String password) {
        try {
            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));

            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            String jwtToken = jwtUtils.generateToken(userDetails.getUsername());

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

        // It could happen, if you call from an endpoint method, where the user isn't authenticated
        throw new IllegalStateException("Illegal state: no authenticated user.");
    }

    @Override
    public User getByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new NoSuchUserException(String.format("No user found with username %s.", username)));
    }

    @Override
    public AlterUserResponseDTO assignRolesToUser(AssignRolesRequestDTO requestDTO) {
        log.info("---| Making a request to the userService.");

        return userServiceWebClient
                .post()
                .uri("/api/roles/assign-to-user")
                .bodyValue(requestDTO)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        res -> res
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(UserServiceException::new)
                                .flatMap(Mono::error))
                .bodyToMono(AlterUserResponseDTO.class)
                .retryWhen(buildRetrySpec())
                .block();
    }

    @Override
    public AlterUserResponseDTO updateFields(User user, PatchUserRequestDTO userRequest) {
        log.info("---| Making a request to the userService.");

        return userServiceWebClient
                .patch()
                .uri(String.format("/api/users/%s", user.getId()))
                .bodyValue(userRequest)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        res -> res
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(UserServiceException::new)
                                .flatMap(Mono::error))
                .bodyToMono(AlterUserResponseDTO.class)
                .retryWhen(buildRetrySpec())
                .block();
    }

    @Override
    public DepositAmountResponseDTO depositAmount(User user, DepositAmountRequestDTO requestDTO) {
        log.info("---| Making a request to the userService.");

        var msvcRequestDTO = new MsvcDepositAmountRequestDTO(
                user.getId(),
                requestDTO.getAmount()
        );

        return userServiceWebClient
                .post()
                .uri("/api/users/deposit")
                .bodyValue(msvcRequestDTO)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        res -> res
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(UserServiceException::new)
                                .flatMap(Mono::error))
                .bodyToMono(DepositAmountResponseDTO.class)
                .retryWhen(buildRetrySpec())
                .block();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getByUsername(username);

        return new CustomUserDetails(user);
    }
}
