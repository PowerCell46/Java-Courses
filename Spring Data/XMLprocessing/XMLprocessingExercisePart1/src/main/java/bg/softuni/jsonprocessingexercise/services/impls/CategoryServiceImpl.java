package bg.softuni.jsonprocessingexercise.services.impls;

import bg.softuni.jsonprocessingexercise.DTOs.CategoryQueryDTO;
import bg.softuni.jsonprocessingexercise.DTOs.CategorySeedDTO;
import bg.softuni.jsonprocessingexercise.entities.Category;
import bg.softuni.jsonprocessingexercise.repositories.CategoryRepository;
import bg.softuni.jsonprocessingexercise.services.interfaces.CategoryService;
import bg.softuni.jsonprocessingexercise.utils.ValidationUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private static final String FILE_PATH = "src/main/resources/static/JSON/categories.json";

    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() > 0) return;

        // reading the data and keeping it in String format
        String jsonContent = new String(Files.readAllBytes(Path.of(FILE_PATH)));
        // parsing the String data to DTOs
        CategorySeedDTO[] categorySeedDTOS = this.gson.fromJson(jsonContent, CategorySeedDTO[].class);
        // iterating through the DTOs
        for (CategorySeedDTO categorySeedDTO : categorySeedDTOS) {
            if (this.validationUtil.isValid(categorySeedDTO)) {
                // if valid -> save to the DB
                Category categoryE = this.modelMapper.map(categorySeedDTO, Category.class);
                this.categoryRepository.saveAndFlush(categoryE);

            } else {
                // if invalid -> print the error message
                this.validationUtil
                    .getViolations(categorySeedDTO)
                    .forEach(validation -> System.out.println(validation.getMessage()));
            }
        }
    }

    @Override
    public long getLastPossibleId() {
        return categoryRepository.count();
    }

    @Override
    public Category findById(long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public boolean exportToJSONCategoriesStatistics() {
        String EXPORT_RESULT_JSON_FILE_PATH =
            "src/main/resources/static/resultJSON/categoriesStatistics.json";

        List<String> jsonObjectsResult = new ArrayList<>();

        try (FileWriter writer = new FileWriter(EXPORT_RESULT_JSON_FILE_PATH)) {
            for (Object[] categoryStatistics : this.categoryRepository.getCategoriesStatistics()) {
                CategoryQueryDTO categoryQueryDTO = CategoryQueryDTO.builder()
                    .category((String) categoryStatistics[0])
                    .productsCount((Long) categoryStatistics[1])
                    .averagePrice((BigDecimal) categoryStatistics[2])
                    .totalRevenue((BigDecimal) categoryStatistics[3])
                    .build();
                jsonObjectsResult.add(gson.toJson(categoryQueryDTO));
            }

            writer.write(String.valueOf(jsonObjectsResult));
            writer.write(System.lineSeparator());

            System.out.println("Successful operation!");
            return true;

        } catch (IOException e) {
            System.out.println("An Error occurred while Writing the Data!");
            return false;
        }
    }
}
