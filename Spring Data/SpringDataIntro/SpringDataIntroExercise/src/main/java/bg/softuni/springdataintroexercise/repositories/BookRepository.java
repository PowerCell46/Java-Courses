package bg.softuni.springdataintroexercise.repositories;

import bg.softuni.springdataintroexercise.entities.Author;
import bg.softuni.springdataintroexercise.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Set<Book> findAllByReleaseDateAfter(LocalDate releaseDate);

    @Query("SELECT b FROM Book b WHERE b.author = :author ORDER BY b.releaseDate DESC, b.title ASC")
    Set<Book> getAllByAuthorOrderByReleaseDateDescAndTitleAsc(Author author);
}
