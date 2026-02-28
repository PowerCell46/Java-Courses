package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.auth.request.AssignRolesRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.DepositAmountRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.msvc.MsvcDepositRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.msvc.MsvcUserCryptoWalletRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.UserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.msvc.MsvcUpdateUserCryptoWalletRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.AlterUserResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.DepositAmountResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.common.ErrorResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.mail.RegisterUserDTO;
import com.ItCareerElevatorSixthExercise.entities.Role;
import com.ItCareerElevatorSixthExercise.entities.User;
import com.ItCareerElevatorSixthExercise.exceptions.EmailIsAlreadyTakenException;
import com.ItCareerElevatorSixthExercise.exceptions.NoSuchUserException;
import com.ItCareerElevatorSixthExercise.exceptions.PaymentServiceException;
import com.ItCareerElevatorSixthExercise.exceptions.UsernameIsAlreadyTakenException;
import com.ItCareerElevatorSixthExercise.repositories.UserRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.RoleService;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.ItCareerElevatorSixthExercise.util.RetryPolicy.buildRetrySpec;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${app.kafka.topics.register-user}")
    private String REGISTER_USER_TOPIC_NAME;

    private final RoleService roleService;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WebClient paymentServiceWebClient;
    private final KafkaTemplate<String, String> registerEmailKafkaTemplate;

    @Override
    public User register(UserRequestDTO requestDTO) {
        validateRegisterData(requestDTO);

        User user = new User(
                requestDTO.getUsername(),
                requestDTO.getEmail(),
                encodePassword(requestDTO.getPassword())
        );
        user = save(user);

        /* if (requestDTO.getWalletAddress() != null) {
            initializeCryptoWalletAddress(user.getId(), requestDTO.getWalletAddress());
        }
        */

        sendSuccessfulRegistrationKafkaMessage(user);

        return user;
    }

    private void validateRegisterData(UserRequestDTO userRequest) {
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new UsernameIsAlreadyTakenException(
                    String.format("User with username %s already exists.", userRequest.getUsername())
            );
        }

        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new EmailIsAlreadyTakenException(
                    String.format("Email %s is already taken.", userRequest.getEmail())
            );
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public User save(User user) {
        log.info("Persisting user with username {} to the database.", user.getUsername());
        return userRepository.save(user);
    }

    private void initializeCryptoWalletAddress(String id, String walletAddress) {
        log.info("---| Making a request to the paymentMicroservice.");

        var requestBody = new MsvcUserCryptoWalletRequestDTO(id, walletAddress);
        paymentServiceWebClient
                .post()
                .uri("/api/users-wallets/crypto")
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        res -> res
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(PaymentServiceException::new)
                                .flatMap(Mono::error))
                .toBodilessEntity()
                .retryWhen(buildRetrySpec())
                .block();
    }

    private void sendSuccessfulRegistrationKafkaMessage(User user) {
        try {
            String key = String.format("register-user-%s-email", user.getId());
            String value = objectMapper.writeValueAsString(new RegisterUserDTO(user.getId(), user.getUsername(), user.getEmail()));

            registerEmailKafkaTemplate
                    .send(REGISTER_USER_TOPIC_NAME, key, value)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to send RegisterUserDTO to topic {}.", REGISTER_USER_TOPIC_NAME, ex);

                        } else {
                            log.info("Success sending RegisterUserDTO to topic {}.", REGISTER_USER_TOPIC_NAME);
                        }
                    });

        } catch (JsonProcessingException ex) {
            log.error("An error occurred with \"objectMapper.writeValueAsString(new RegisterUserDTO(user.getId(), user.getUsername(), user.getEmail()))\".");
        }
    }

    @Override
    public AlterUserResponseDTO assignRolesToUser(AssignRolesRequestDTO requestDTO) {
        User user = getByUsername(requestDTO.getUsername());

        requestDTO
                .getRoles()
                .forEach(roleName -> {
                    Role role = roleService.getByName(roleName);
                    user.getRoles().add(role);
                });
        save(user);

        return new AlterUserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    private User getByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new NoSuchUserException(String.format("No user found with username %s.", username)));
    }

    @Override
    public AlterUserResponseDTO updateFields(String userId, UserRequestDTO requestDTO) {
        User user = userRepository
                .findById(userId)
                .orElseThrow((() -> new NoSuchUserException(String.format("No user found with id %s.", userId))));

        if (requestDTO.getUsername() != null) {
            if (userRepository.findByUsername(requestDTO.getUsername()).isPresent()) {
                throw new UsernameIsAlreadyTakenException(
                        String.format("User with username %s already exists.", requestDTO.getUsername())
                );
            }
            user.setUsername(requestDTO.getUsername());
        }

        if (requestDTO.getEmail() != null) {
            if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
                throw new EmailIsAlreadyTakenException(
                        String.format("Email %s is already taken.", requestDTO.getEmail()));
            }
            user.setEmail(requestDTO.getEmail());
        }

        if (requestDTO.getPassword() != null) { // ? Normally this would happen by a link sent to the email for resetting the password
            user.setPassword(encodePassword(requestDTO.getPassword()));
        }

        if (requestDTO.getUsername() != null || requestDTO.getEmail() != null || requestDTO.getPassword() != null) {
            log.info("Updating user with username {}.", user.getUsername());
            save(user);
        }

        /* if (requestDTO.getWalletAddress() != null) {
            updateCryptoWalletAddress(userId, requestDTO.getWalletAddress());
        }
        */

        return new AlterUserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    @Override
    public DepositAmountResponseDTO depositAmount(DepositAmountRequestDTO requestDTO) {
        log.info("---| Making a request to the paymentMicroservice.");

        var requestBody = new MsvcDepositRequestDTO(requestDTO.getAmount());

        return paymentServiceWebClient
                .patch()
                .uri(String.format("/api/users-wallets/local/%s", requestDTO.getUserId()))
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        res -> res
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(PaymentServiceException::new)
                                .flatMap(Mono::error))
                .bodyToMono(DepositAmountResponseDTO.class)
                .retryWhen(buildRetrySpec())
                .block();
    }

    private void updateCryptoWalletAddress(String id, String walletAddress) {
        log.info("---| Making a request to the paymentMicroservice.");

        var requestBody = new MsvcUpdateUserCryptoWalletRequestDTO(walletAddress);

        paymentServiceWebClient
                .patch()
                .uri(String.format("/api/users-wallets/crypto/%s", id))
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        res -> res
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(PaymentServiceException::new)
                                .flatMap(Mono::error))
                .toBodilessEntity()
                .retryWhen(buildRetrySpec())
                .block();
    }
}
