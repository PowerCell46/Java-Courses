package bg.softuni.springdataadvancedqueryingexercise.services.interfaces;

import bg.softuni.springdataadvancedqueryingexercise.entities.Author;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface AuthorService {

    Set<Author> findAllByFirstNameEndingWith(String substring);

    List<Object[]> getAllAuthorsWithTheirNumberOfBooks();
}
