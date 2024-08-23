package bg.softuni.springdataintroexercise.repositories;

import bg.softuni.springdataintroexercise.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a JOIN a.books b WHERE b.releaseDate < :givenYear")
    Set<Author> findAuthorsWithBooksReleasedBefore(LocalDate givenYear);

    @Query("SELECT a, count(b.id) FROM Author a JOIN a.books b GROUP BY(a) ORDER BY count(b.id) DESC")
    Set<Object[]> getAllAuthorsWithNumberOfBooks();


    Author findByFirstNameAndLastName(String firstName, String lastName);
}
