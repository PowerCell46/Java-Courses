package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.implementations;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.AuthResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.UserRequestDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.entities.User;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.exceptions.InvalidCredentialsException;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.exceptions.UserAlreadyExistsException;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.repositories.UserRepository;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.services.interfaces.UserService;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.utils.CustomUserDetails;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;

    private final UserRepository userRepository;

    @Override
    public AuthResponseDTO register(UserRequestDTO userRequest) {
        if (findByUsername(userRequest.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(
                    String.format("User with username: %s already exists.", userRequest.getUsername())
            );
        }

        User user = constructNonPersistedUser(userRequest);

        user = save(user);

        return authenticate(user.getUsername(), userRequest.getPassword());
    }

    private User constructNonPersistedUser(UserRequestDTO userRequest) {
        String encodedPassword = encodePassword(userRequest.getPassword());

        return new User(userRequest.getUsername(), encodedPassword);
    }

    private String encodePassword(String password) {
        return encoder.encode(password);
    }

    @Override
    public User save(User user) {
        log.info("Persisting user with username '{}' to the Database.", user.getUsername());

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

        throw new IllegalStateException("No authenticated user.");
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
