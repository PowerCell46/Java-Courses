package softuni.exam.service.impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.StarSeedDTO;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.service.StarService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class StarServiceImpl implements StarService {
    private final StarRepository starRepository;
    private final ConstellationService constellationService;
    private final Gson gson;

    @Override
    public boolean areImported() {
        return starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        final String FILE_PATH = "src/main/resources/files/json/stars.json";

        String jsonData = new String(Files.readAllBytes(Path.of(FILE_PATH)));
        return jsonData;
    }

    @Override
    public String importStars() throws IOException {
        StarSeedDTO[] starSeedDTOS = gson.fromJson(
            readStarsFileContent(),
            StarSeedDTO[].class
        );
        StringBuilder resultString = new StringBuilder();

        for (StarSeedDTO starSeedDTO : starSeedDTOS) {

            try {
                StarSeedDTO finalDTO = new StarSeedDTO(
                    starSeedDTO.getDescription(),
                    starSeedDTO.getLightYears(),
                    starSeedDTO.getName(),
                    starSeedDTO.getStarType(),
                    starSeedDTO.getConstellation()
                );

                Star star = new Star();
                star.setDescription(starSeedDTO.getDescription());
                star.setLightYears(starSeedDTO.getLightYears());
                star.setName(starSeedDTO.getName());
                star.setStarType(starSeedDTO.getStarType());
                star.setConstellation(
                    constellationService
                    .findById(starSeedDTO.getConstellation())
                    .orElse(null)
                );

                starRepository.saveAndFlush(star);

                resultString.append("Successfully imported star ")
                    .append(star.getName())
                    .append(" - ")
                    .append(star.getLightYears())
                    .append(" light years");

            } catch (IllegalArgumentException | DataIntegrityViolationException e) {
                resultString
                    .append("Invalid star\n");
            }
        }

        return resultString
            .toString()
            .trim();
    }

    @Override
    public String exportStars() {
        return "";
    }
}
