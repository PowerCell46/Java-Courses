package bg.softuni.springdataintro.services.impl;

import bg.softuni.springdataintro.data.entities.User;
import bg.softuni.springdataintro.data.repositories.UserRepository;
import bg.softuni.springdataintro.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }
}
