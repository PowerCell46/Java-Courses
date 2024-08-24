package bg.softuni.automappingobjectsexercise;

import bg.softuni.automappingobjectsexercise.entities.Game;
import bg.softuni.automappingobjectsexercise.entities.User;
import bg.softuni.automappingobjectsexercise.services.interfaces.GameService;
import bg.softuni.automappingobjectsexercise.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final UserService userService;
    private final GameService gameService;
    private User loggedInUser;
    private List<String> shoppingCart;

    public CommandLineRunnerImpl(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
        this.shoppingCart = new LinkedList<>();
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String[] currentData = reader.readLine().split("\\|");

            if (currentData[0].equals("END")) {
                System.out.println("Program was terminated.");
                break;
            }

            switch (currentData[0]) {
//                Authentication
                case "RegisterUser":
                    boolean userResult = userService
                            .registerUser(
                                currentData[1],
                                currentData[2],
                                currentData[3],
                                currentData[4]
                            );
                    System.out.println("User was Registered " + (userResult ? "successfully." : "unsuccessfully."));
                    break;

                case "LoginUser":
                    User user = userService.loginUser(currentData[1], currentData[2]);

                    if (user == null) {
                        System.out.println("Incorrect username / password");

                    } else {
                        if (loggedInUser == user) {
                            System.out.println("User is already Logged in.");

                        } else {
                            loggedInUser = user;
                            System.out.println(currentData[1] + " successfully Logged in.");
                        }
                    }
                    break;

                case "Logout":
                    if (loggedInUser == null) {
                        System.out.println("Cannot log out. No user was logged in.");

                    } else {
                        System.out.println("User " + loggedInUser.getFullName() + " successfully Logged out.");
                        loggedInUser = null;
                        shoppingCart = new LinkedList<>();
                    }
                    break;
//                    End of Authentication

//                    Games
                case "AddGame":
                    if (loggedInUser == null) {
                        System.out.println("You are not authenticated!");
                        break;
                    }
                    if (!loggedInUser.isAdministrator()) {
                        System.out.println("You don't have the permissions to Add a Game.");
                        break;
                    }

                    boolean gameResult = gameService.addGame(
                        currentData[1], new BigDecimal(currentData[2]),
                        Double.parseDouble(currentData[3]), currentData[4], currentData[5],
                        currentData[6], currentData[7]
                    );
                    System.out.println("Game created " + (gameResult ? "successfully." : "unsuccessfully."));
                    break;

                case "EditGame":
                    if (loggedInUser == null) {
                        System.out.println("You are not authenticated!");
                        break;
                    }
                    if (!loggedInUser.isAdministrator()) {
                        System.out.println("You don't have the permissions to Edit a Game.");
                        break;
                    }

                    boolean updateResult = gameService.updateGame(Long.parseLong(currentData[1]), currentData);
                    System.out.println("Game updated " + (updateResult ? "successfully." : "unsuccessfully."));
                    break;

                case "DeleteGame":
                    if (loggedInUser == null) {
                        System.out.println("You are not authenticated!");
                        break;
                    }
                    if (!loggedInUser.isAdministrator()) {
                        System.out.println("You don't have the permissions to Delete a Game!");
                        break;
                    }

                    boolean deleteResult = gameService.deleteGameById(Long.parseLong(currentData[1]));

                    System.out.println("Game deleted " + (deleteResult ? "successfully." : "unsuccessfully."));
                    break;

                case "AllGames":
                    List<Game> games = gameService.getAllGames();

                    for (Game game : games) {
                        System.out.printf("%s %.2f\n", game.getTitle(), game.getPrice());
                    }
                    break;

                case "DetailsGame":
                    Optional<Game> game = gameService.getGameByTitle(currentData[1]);

                    if (game.isEmpty())  {
                        System.out.println("No such Game was found.");

                    } else {
                        System.out.println(game.get());
                    }
                    break;
//                    End of Games

//                    Cart
                case "AddItem":
                    if (loggedInUser == null) {
                        System.out.println("You are not authenticated!");
                        break;
                    }

                    Optional<Game> gameToAdd = gameService.getGameByTitle(currentData[1]);
                    if (gameToAdd.isEmpty()) {
                        System.out.println("No such Game was found.");

                    } else if (shoppingCart.contains(currentData[1])) {
                        System.out.println("Game is already added to Cart.");

                    } else {
                        shoppingCart.add(currentData[1]);
                        System.out.println(currentData[1] + " was successfully added to Cart.");
                    }
                    break;

                case "RemoveItem":
                    if (loggedInUser == null) {
                        System.out.println("You are not authenticated!");
                        break;
                    }

                    if (shoppingCart.contains(currentData[1])) {
                        shoppingCart.remove(currentData[1]);
                        System.out.println("Game " + currentData[1] + " was successfully removed from Cart!");

                    } else {
                        System.out.println("There is no such Game in the Cart.");
                    }
                    break;

                case "BuyItem":
                    if (loggedInUser == null) {
                        System.out.println("You are not authenticated!");
                        break;
                    }

                    List<String> boughtGames = userService.finishCartOrder(loggedInUser, shoppingCart);

                    if (boughtGames.isEmpty()) {
                        System.out.println("No Games were bought.");

                    } else {
                        System.out.print("Successfully bought games:");
                        boughtGames.forEach(gameName -> System.out.format("\n   -%s", gameName));
                        System.out.println();
                    }
                    break;
//                    End of Cart

                case "OwnedGames":
                    if (loggedInUser == null) {
                        System.out.println("You are not authenticated!");
                        break;
                    }

                    List<String> userGames = userService.getUserGames(loggedInUser);
                    if (userGames.isEmpty()) {
                        System.out.println("Current User doesn't have any Games at this point.");

                    } else {
                        for (String userGame : userGames) {
                            System.out.println(userGame);
                        }
                    }
                    break;
            }
        }
    }
}
