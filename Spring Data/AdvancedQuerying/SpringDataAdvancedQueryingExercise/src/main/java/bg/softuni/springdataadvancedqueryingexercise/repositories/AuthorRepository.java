package bg.softuni.springdataadvancedqueryingexercise.repositories;

import bg.softuni.springdataadvancedqueryingexercise.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Set<Author> findAllByFirstNameEndsWith(String substring);

    @Query("SELECT a, COUNT(b) FROM Author a JOIN a.books b GROUP BY a ORDER BY COUNT(b) DESC")
    List<Object[]> getAllAuthorsWithTheirNumberOfBooksDesc();

}
