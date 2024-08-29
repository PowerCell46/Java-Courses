package bg.softuni.xmlprocessingexercise.services.interfaces;

import bg.softuni.xmlprocessingexercise.entities.Category;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public interface CategoryService {

    void seedCategories() throws JAXBException;

    long getLastPossibleId();

    Category findById(long id);

    boolean exportToXMLCategoriesStatistics();
}
