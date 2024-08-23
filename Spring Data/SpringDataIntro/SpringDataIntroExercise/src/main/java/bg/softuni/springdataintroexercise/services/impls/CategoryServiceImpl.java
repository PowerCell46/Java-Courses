package bg.softuni.springdataintroexercise.services.impls;

import bg.softuni.springdataintroexercise.entities.Category;
import bg.softuni.springdataintroexercise.repositories.CategoryRepository;
import bg.softuni.springdataintroexercise.services.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private static final String CATEGORIES_FILE_PATH = "src/main/resources/files/categories.txt";

    @Override
    public void seedCategories() throws IOException {
        if (this.categoryRepository.count() == 0) {
            Files.readAllLines(Path.of(CATEGORIES_FILE_PATH)).stream().filter(row -> !row.isEmpty())
                .forEach(row -> this.categoryRepository.saveAndFlush(new Category(row)));
        }
    }

    @Override
    public Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();

        int numberOfCategories = new Random().nextInt(0, 4);

        for (int i = 0; i < numberOfCategories; i++) {
            long randomId = new Random().nextLong(0L, this.categoryRepository.count() + 1L);
            categories.add(this.categoryRepository.findById(randomId).orElse(null));
        }

        return categories;
    }
}
