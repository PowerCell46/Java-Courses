package bg.softuni.springdataintroexercise.services.interfaces;

import bg.softuni.springdataintroexercise.entities.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {

    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();
}
