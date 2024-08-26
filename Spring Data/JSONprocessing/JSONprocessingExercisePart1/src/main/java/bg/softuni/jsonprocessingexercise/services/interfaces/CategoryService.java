package bg.softuni.jsonprocessingexercise.services.interfaces;

import bg.softuni.jsonprocessingexercise.entities.Category;

import java.io.IOException;

public interface CategoryService {

    void seedCategories() throws IOException;

    long getLastPossibleId();

    Category findById(long id);

    boolean exportToJSONCategoriesStatistics();
}
