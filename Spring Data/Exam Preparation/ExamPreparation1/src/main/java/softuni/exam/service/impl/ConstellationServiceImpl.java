package softuni.exam.service.impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ConstellationSeedDTO;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ConstellationServiceImpl implements ConstellationService {
    private final ConstellationRepository constellationRepository;
    private final Gson gson;

    @Override
    public boolean areImported() {
        return constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        final String FILE_PATH = "src/main/resources/files/json/constellations.json";

        String jsonData = new String(Files.readAllBytes(Path.of(FILE_PATH)));
        return jsonData;
    }

    @Override
    public String importConstellations() throws IOException {
        ConstellationSeedDTO[] constellations = gson.fromJson(
            readConstellationsFromFile(),
            ConstellationSeedDTO[].class
        );
        StringBuilder resultString = new StringBuilder();

        for (ConstellationSeedDTO constellationSeedDTO : constellations) {

            try {
                ConstellationSeedDTO finalDTO = new ConstellationSeedDTO(
                    constellationSeedDTO.getName(),
                    constellationSeedDTO.getDescription()
                );

                Constellation constellation = new Constellation();
                constellation.setName(finalDTO.getName());
                constellation.setDescription(finalDTO.getDescription());

                constellationRepository.saveAndFlush(constellation);
                resultString
                    .append("Successfully imported constellation ")
                    .append(constellationSeedDTO.getName())
                    .append(" - ")
                    .append(constellationSeedDTO.getDescription())
                    .append(System.lineSeparator());

            } catch (IllegalArgumentException | DataIntegrityViolationException e) {
                resultString
                    .append("Invalid constellation\n");
            }
        }

        return resultString
            .toString()
            .trim();
    }

    @Override
    public Optional<Constellation> findById(long id) {
        return constellationRepository.findById(id);
    }
}
