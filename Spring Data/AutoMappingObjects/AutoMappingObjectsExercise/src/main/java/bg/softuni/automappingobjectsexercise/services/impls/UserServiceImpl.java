package bg.softuni.automappingobjectsexercise.services.impls;

import bg.softuni.automappingobjectsexercise.entities.Game;
import bg.softuni.automappingobjectsexercise.entities.Order;
import bg.softuni.automappingobjectsexercise.entities.User;
import bg.softuni.automappingobjectsexercise.repositories.UserRepository;
import bg.softuni.automappingobjectsexercise.services.interfaces.GameService;
import bg.softuni.automappingobjectsexercise.services.interfaces.OrderService;
import bg.softuni.automappingobjectsexercise.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GameService gameService;
    private final OrderService orderService;

    @Override
    public boolean registerUser(String email, String password, String confirmPassword, String fullName) {
        if (!validateEmail(email) || !validatePassword(password) || !comparePasswords(password, confirmPassword)) {
            return false;
        }

        User user = new User(email, password, fullName);
        userRepository.save(user);
        return true;
    }

    @Override
    public User loginUser(String email, String password) {
        Optional<User> user = userRepository.findUserByEmailAndPassword(email, password);

        return user.orElse(null);
    }

    @Override
    public List<String> finishCartOrder(User user, List<String> shoppingCart) {
        List<String> boughtGames = new ArrayList<>();
        Order order = new Order(user);

        for (String gameName : shoppingCart) {
            Game game = gameService.getGameByTitle(gameName).get();
            if (!user.getGames().contains(game)) {
                user.getGames().add(game); // add game to the users_games junction table
                order.getGames().add(game); // add game to the orders_games junction table
                boughtGames.add(gameName); // add game name to the result list
            }
        }

        userRepository.save(user);
        orderService.saveOrder(order);
        return boughtGames;
    }

    @Override
    public List<String> getUserGames(User user) {
        List<String> gameNames = new ArrayList<>();
        for (Game game : user.getGames()) {
            gameNames.add(game.getTitle());
        }

        return gameNames;
    }

    private static boolean validateEmail(String email) {
        boolean result = email.contains("@") && email.contains(".");
        if (!result) System.out.println("Incorrect email.");

        return result;
    }

    private static boolean validatePassword(String password) {
        String lowerCasePattern = ".*[a-z].*";
        String upperCasePattern = ".*[A-Z].*";
        String digitPattern = ".*\\d.*";

        boolean hasLowerCase = password.matches(lowerCasePattern);
        boolean hasUpperCase = password.matches(upperCasePattern);
        boolean hasDigit = password.matches(digitPattern);

        boolean result = hasLowerCase && hasUpperCase && hasDigit && password.length() >= 6;
        if (!result) System.out.println("Incorrect password.");

        return result;
    }

    private static boolean comparePasswords(String password, String confirmPass) {
        boolean result = password.equals(confirmPass);
        if (!result) System.out.println("Password and confirm password must match.");

        return result;
    }
}
