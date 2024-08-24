package bg.softuni.automappingobjectsexercise.services.impls;

import bg.softuni.automappingobjectsexercise.entities.Game;
import bg.softuni.automappingobjectsexercise.repositories.GameRepository;
import bg.softuni.automappingobjectsexercise.services.interfaces.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    @Override
    public boolean addGame(String title, BigDecimal price, double size, String trailer, String thumbnailUrl, String description, String releaseDate) {
        if (!validateTitle(title) || !validatePrice(price) || !validateSize(size) ||
            !validateTrailer(trailer) || !validateThumbnailURL(thumbnailUrl) || !validateDescription(description)
        ) {
            return false;
        }

        Game game = new Game(
            title, trailer.substring(trailer.length() - 11),
            thumbnailUrl, size, price, description, LocalDate.of(
                Integer.parseInt(releaseDate.split("-")[2]),
                Integer.parseInt(releaseDate.split("-")[1]),
                Integer.parseInt(releaseDate.split("-")[0]))
        );

        gameRepository.save(game);
        return true;
    }

    @Transactional
    @Override
    public boolean updateGame(long id, String... values) {
        Optional<Game> game = gameRepository.findById(id);
        if (game.isEmpty()) return false; // Game doesn't exist

        for (int i = 2; i < values.length; i++) {
            String[] currentData = values[i].split("=");

            switch (currentData[0]) {
                case "price":
                    BigDecimal newPrice = new BigDecimal(currentData[1]);
                    if (!validatePrice(newPrice)) continue;

                    int updatePriceById = gameRepository.updatePriceById(newPrice, id);
                    if (updatePriceById != 1) return false;
                    break;

                case "size":
                    double newSizeValue = Double.parseDouble(currentData[1]);
                    if (!validateSize(newSizeValue)) continue;

                    int updateSizeById = gameRepository.updateSizeById(newSizeValue, id);
                    if (updateSizeById != 1) return false;
                    break;

                case "releaseDate":
                    LocalDate releaseDate = LocalDate.of(
                        Integer.parseInt(currentData[1].split("-")[2]),
                        Integer.parseInt(currentData[1].split("-")[1]),
                        Integer.parseInt(currentData[1].split("-")[0])
                    );

                    int updateReleaseDateById = gameRepository.updateReleaseDateById(releaseDate, id);
                    if (updateReleaseDateById != 1) return false;
                    break;

                default:
                    switch (currentData[0]) {
                        case "title":
                            if (!validateTitle(currentData[1])) continue;
                            break;
                        case "trailer":
                            if (!validateTrailer(currentData[1])) continue;
                            break;
                        case "thumbnailURL":
                            if (!validateThumbnailURL(currentData[1])) continue;
                            break;
                        case "description":
                            if (!validateDescription(currentData[1])) continue;
                    }

                    int updateStringValueById = gameRepository.updateStringValueById(currentData[0], currentData[1], id);
                    if (updateStringValueById != 1) return false;
                    break;
            }
        }

        return true;
    }

    @Transactional
    @Override
    public boolean deleteGameById(long id) {
        Optional<Game> game = gameRepository.findById(id);
        if (game.isEmpty()) return false; // Game doesn't exist

        gameRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public Optional<Game> getGameByTitle(String title) {
        return gameRepository.findByTitle(title);
    }

    private static boolean validateTitle(String title) {
        boolean result = title.charAt(0) > 'A' && title.charAt(0) < 'Z' && title.length() >= 3 && title.length() <= 100;
        if (!result) System.out.println("Invalid title.");

        return result;
    }

    private static boolean validateThumbnailURL(String thumbnailUrl) {
        boolean result = thumbnailUrl.startsWith("http://") || thumbnailUrl.startsWith("https://");
        if (!result) System.out.println("Invalid Thumbnail URL.");

        return result;
    }

    private static boolean validatePrice(BigDecimal price) {
        boolean result = !(price.compareTo(BigDecimal.ZERO) < 0);
        if (!result) System.out.println("Invalid price.");

        return result;
    }

    private static boolean validateSize(double size) {
        boolean result = size >= 0;
        if (!result) System.out.println("Invalid size.");

        return result;
    }

    private static boolean validateTrailer(String trailer) {
        boolean result = trailer.length() >= 11;
        if (!result) System.out.println("Invalid trailer length.");

        return result;
    }

    private static boolean validateDescription(String description) {
        boolean result = description.length() >= 20;
        if (!result) System.out.println("Invalid description length.");

        return result;
    }
}
