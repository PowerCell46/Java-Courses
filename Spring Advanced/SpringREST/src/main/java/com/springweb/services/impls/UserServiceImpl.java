package com.springweb.services.impls;

import com.springweb.models.DTOs.UserRequestDTO;
import com.springweb.configs.JsonplaceholderConfig;
import com.springweb.models.entities.User;
import com.springweb.repositories.UserRepository;
import com.springweb.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final RestClient restClient;
    private final JsonplaceholderConfig jsonplaceholderConfig;

    @Override
    public boolean hasInitializedExRates() {
        return userRepository.count() > 0;
    }

    @Override
    public UserRequestDTO[] fetchUsers() {
        return restClient
                .get()
                .uri(jsonplaceholderConfig.getUsersUrl())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(UserRequestDTO[].class);
    }

    @Override
    public void seedUsers() {
        if (hasInitializedExRates()) return;
        LOGGER.info("Updating Users...");

        for (UserRequestDTO userRequestDTO : fetchUsers()) {
            userRepository.save(
                    User
                            .builder()
                            .firstName(userRequestDTO.getFirstname())
                            .lastName(userRequestDTO.getLastname())
                            .email(userRequestDTO.getEmail())
                            .birthdate(userRequestDTO.getBirthDate())
                            .phone(userRequestDTO.getPhone())
                            .website(userRequestDTO.getWebsite())
                            .build()
            );
        }
        LOGGER.info("Inserted {} entries", userRepository.count());
    }

    @Override
    public UserRequestDTO getUserById(int id) {
        return userRepository
                .findById(id)
                .map(user ->
                        UserRequestDTO
                                .builder()
                                .id(user.getId())
                                .firstname(user.getFirstName())
                                .email(user.getEmail())
                                .lastname(user.getLastName())
                                .birthDate(user.getBirthdate())
                                .phone(user.getPhone())
                                .website(user.getWebsite())
                                .build())
                .orElse(
                        restClient
                                .get()
                                .uri(String.format("%s/%d", jsonplaceholderConfig.getUsersUrl(), id))
                                .accept(MediaType.APPLICATION_JSON)
                                .retrieve()
                                .body(UserRequestDTO.class));
    }
}
