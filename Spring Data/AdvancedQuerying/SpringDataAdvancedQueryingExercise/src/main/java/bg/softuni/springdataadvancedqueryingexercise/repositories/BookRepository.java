package bg.softuni.springdataadvancedqueryingexercise.repositories;

import bg.softuni.springdataadvancedqueryingexercise.entities.Book;
import bg.softuni.springdataadvancedqueryingexercise.entities.enums.AgeRestriction;
import bg.softuni.springdataadvancedqueryingexercise.entities.enums.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Set<Book> findAllByAgeRestrictionEquals(AgeRestriction ageRestriction);

    Set<Book> findAllByCopiesLessThanAndEditionTypeEquals(int copies, EditionType editionType);

    Set<Book> findAllByPriceGreaterThanOrPriceIsLessThan(BigDecimal price, BigDecimal price2);

    @Query("SELECT b FROM Book b WHERE YEAR(b.releaseDate) != :year")
    Set<Book> findAllByReleaseDateYearNotEquals(int year);

    Set<Book> findAllByReleaseDateBefore(LocalDate releaseDate);

    Set<Book> findAllByTitleContainingIgnoreCase(String substring);


    @Query("SELECT b FROM Book b JOIN b.author WHERE b.author.lastName LIKE :substring")
    Set<Book> findAllBooksByAuthorLastNameLike(String substring);

    @Query("SELECT COUNT(b) FROM Book b WHERE LENGTH(b.title) > :givenLength")
    int getNumberOfBooksWithTitleLengthMoreThan(int givenLength);

    Book findByTitle(String title);

    Set<Book> findAllBooksByReleaseDateAfter(LocalDate releaseDate);

    @Transactional
    @Modifying
    int deleteAllByCopiesLessThan(int copies);

    @Procedure(name = "sp_get_authors_number_of_books")
    @Transactional
    int callGetAuthorsNumberOfBooks(String first_name, String last_name);

        //    DELIMITER $$
        //    CREATE PROCEDURE sp_get_authors_number_of_books(
        //        IN author_first_name VARCHAR(255),
        //        IN author_last_name VARCHAR(255),
        //        OUT number_of_books INT)
        //    BEGIN
        //        SELECT
        //            COUNT(*)
        //        FROM
        //            authors
        //        JOIN books on
        //            authors.id = books.author_id
        //        WHERE
        //            first_name = author_first_name AND
        //            last_name = author_last_name;
        //    END;
        //    DELIMITER ;
}