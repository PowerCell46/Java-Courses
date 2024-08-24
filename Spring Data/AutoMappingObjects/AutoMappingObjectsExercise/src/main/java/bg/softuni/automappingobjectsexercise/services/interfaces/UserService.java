package bg.softuni.automappingobjectsexercise.services.interfaces;

import bg.softuni.automappingobjectsexercise.entities.User;

import java.util.List;


public interface UserService {

    boolean registerUser(String email, String password, String confirmPassword, String fullName);

    User loginUser(String email, String password);

    List<String> finishCartOrder(User user, List<String> shoppingCart);

    List<String> getUserGames(User user);
}
