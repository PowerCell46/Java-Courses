package bg.softuni.springdataintroexercise.services.interfaces;

import bg.softuni.springdataintroexercise.entities.Author;

import java.io.IOException;
import java.util.Set;

public interface AuthorService {

    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    Set<Author> getAllAuthorsWithAtLeastOneBookBefore1990();


    Set<Object[]> getAllAuthorsWithTheirNumberOfBooks();

    Author findByFullName(String fullName);
}
