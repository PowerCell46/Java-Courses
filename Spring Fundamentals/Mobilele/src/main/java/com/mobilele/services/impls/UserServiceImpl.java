package com.mobilele.services.impls;

import com.mobilele.models.entities.User;
import com.mobilele.repositories.UserRepository;
import com.mobilele.services.interfaces.RolesService;
import com.mobilele.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RolesService rolesService;

    @Override
    public void seedUsers() {
        if (userRepository.count() > 0) return;

        List<User> initUsers = List.of(
            User.builder()
                .username("PowerCell46")
                .firstName("Peter")
                .lastName("Gerdzhikov")
                .imageUrl("https://localhost:8080/images/firstImage")
                .password("Password1")
                .isActive(true)
                .role(rolesService.findById(1).get())
                .build(),

            User.builder()
                .username("MariaDB")
                .firstName("Maria")
                .lastName("Petrova")
                .imageUrl("https://localhost:8080/images/secondImage")
                .password("Password2")
                .isActive(true)
                .role(rolesService.findById(2).get())
                .build(),

            User.builder()
                .username("JohnyBravo")
                .firstName("John")
                .lastName("Doe")
                .imageUrl("https://localhost:8080/images/thirdImage")
                .password("Password3")
                .isActive(false)
                .role(rolesService.findById(2).get())
                .build(),

            User.builder()
                .username("AnaBate")
                .firstName("Anna")
                .lastName("Smith")
                .imageUrl("https://localhost:8080/images/fourthImage")
                .password("Password4")
                .isActive(true)
                .role(rolesService.findById(2).get())
                .build(),

            User.builder()
                .username("MetallicaBros")
                .firstName("James")
                .lastName("Brown")
                .imageUrl("https://localhost:8080/images/fifthImage")
                .password("Password5")
                .isActive(true)
                .role(rolesService.findById(2).get())
                .build(),

            User.builder()
                .username("EmilyBate")
                .firstName("Emily")
                .lastName("Davis")
                .imageUrl("https://localhost:8080/images/sixthImage")
                .password("Password6")
                .isActive(false)
                .role(rolesService.findById(2).get())
                .build()
        );

        userRepository.saveAllAndFlush(initUsers);
    }
}
