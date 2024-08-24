package bg.softuni.springdataadvancedqueryingexercise.services.interfaces;

import bg.softuni.springdataadvancedqueryingexercise.DTOs.BookDTO;
import bg.softuni.springdataadvancedqueryingexercise.entities.Book;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface BookService {

    Set<Book> findAllBooksByAgeRestrictionCaseInsensitive(String ageRestriction);

    Set<Book> findAllGoldenEditionBooksWithLessThan5000Copies();

    Set<Book> findAllBooksCheaperThan5OrMoreExpensiveThan40();

    Set<Book> findAllBooksByReleaseYearDifferentThan(int year);

    Set<Book> findAllBooksByReleaseDateBefore(String year);

    Set<Book> findAllBooksByTitleContainingCaseInsensitive(String substring);

    Set<Book> findAllBooksWithAuthorLastNameStartingWith(String substring);

    int getNumberOfBooksWithTitleLengthMoreThan(int titleLength);

    BookDTO getBookByTitle(String title);

    int addCopiesToBooksReleasedAfter(String date, int numberOfCopies);

    int deleteAllBooksByNumberOfCopiesLessThan(int numberOfCopies);

    int getNumberOfBooksForAnAuthor(String firstName, String lastName);
}
