package com.mobilele.services.impls;

import com.mobilele.models.DTOs.UserLoginDTO;
import com.mobilele.models.DTOs.UserRegisterDTO;
import com.mobilele.models.entities.User;
import com.mobilele.repositories.UserRepository;
import com.mobilele.services.CurrentUser;
import com.mobilele.services.interfaces.RolesService;
import com.mobilele.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RolesService rolesService;

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUser currentUser;

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


    public void registerUser(UserRegisterDTO userRegisterDTO) {

        userRepository.save(map(userRegisterDTO));
    }

    @Override
    public boolean login(UserLoginDTO userLoginDTO) {
        if (userLoginDTO.getPassword() == null) return false;

        System.out.println(userLoginDTO.getUsername());
        User user = userRepository.findByUsername(userLoginDTO.getUsername()).orElse(null);

        System.out.println(user.getFirstName());
        System.out.println(user.getPassword());

        boolean passwordCorrect = passwordEncoder.matches(userLoginDTO.getPassword(),
                user.getPassword()
        );

        if (passwordCorrect) {
            currentUser.setFullName(user.getFirstName() + " " + user.getLastName());
            currentUser.setLoggedIn(true);
            return true;

        } else {
            currentUser.setFullName(null);
            currentUser.setLoggedIn(false);
            return false;
        }
    }

    private User map(UserRegisterDTO userRegisterDTO) {
        User entity = modelMapper.map(userRegisterDTO, User.class);
        entity.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        return entity;
    }
}
