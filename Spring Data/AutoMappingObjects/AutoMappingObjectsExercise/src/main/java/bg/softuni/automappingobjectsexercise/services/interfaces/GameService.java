package bg.softuni.automappingobjectsexercise.services.interfaces;

import bg.softuni.automappingobjectsexercise.entities.Game;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface GameService {

    boolean addGame(String title, BigDecimal price, double size, String trailer, String thumbnailUrl, String description, String releaseDate);

    boolean updateGame(long id, String... values);

    boolean deleteGameById(long id);

    List<Game> getAllGames();

    Optional<Game> getGameByTitle(String title);
}
