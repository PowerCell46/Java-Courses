package bg.softuni.springdataadvancedqueryingexercise.services.impls;

import bg.softuni.springdataadvancedqueryingexercise.DTOs.BookDTO;
import bg.softuni.springdataadvancedqueryingexercise.entities.Book;
import bg.softuni.springdataadvancedqueryingexercise.entities.enums.AgeRestriction;
import bg.softuni.springdataadvancedqueryingexercise.entities.enums.EditionType;
import bg.softuni.springdataadvancedqueryingexercise.repositories.BookRepository;
import bg.softuni.springdataadvancedqueryingexercise.services.interfaces.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Set<Book> findAllBooksByAgeRestrictionCaseInsensitive(String ageRestriction) {
        Map<String, AgeRestriction> ageRestrictionMap = Map.of(
        "minor", AgeRestriction.MINOR, "teen", AgeRestriction.TEEN, "adult", AgeRestriction.ADULT
        );

        return bookRepository.findAllByAgeRestrictionEquals(
            ageRestrictionMap.get(ageRestriction.toLowerCase())
        );
    }

    @Override
    public Set<Book> findAllGoldenEditionBooksWithLessThan5000Copies() {
        return bookRepository.findAllByCopiesLessThanAndEditionTypeEquals(5000, EditionType.GOLD);
    }

    @Override
    public Set<Book> findAllBooksCheaperThan5OrMoreExpensiveThan40() {
        return bookRepository.findAllByPriceGreaterThanOrPriceIsLessThan(BigDecimal.valueOf(40), BigDecimal.valueOf(5));
    }

    @Override
    public Set<Book> findAllBooksByReleaseYearDifferentThan(int year) {
        return bookRepository.findAllByReleaseDateYearNotEquals(year);
    }

    @Override
    public Set<Book> findAllBooksByReleaseDateBefore(String year) {
        return bookRepository.findAllByReleaseDateBefore(LocalDate.of(
                Integer.parseInt(year.split("-")[2]),
                Integer.parseInt(year.split("-")[1]),
                Integer.parseInt(year.split("-")[0])
        ));
    }

    @Override
    public Set<Book> findAllBooksByTitleContainingCaseInsensitive(String substring) {
        return bookRepository.findAllByTitleContainingIgnoreCase(substring);
    }

    @Override
    public Set<Book> findAllBooksWithAuthorLastNameStartingWith(String substring) {
        return bookRepository.findAllBooksByAuthorLastNameLike(substring + "%");
    }

    @Override
    public int getNumberOfBooksWithTitleLengthMoreThan(int titleLength) {
        return bookRepository.getNumberOfBooksWithTitleLengthMoreThan(titleLength);
    }

    @Override
    public BookDTO getBookByTitle(String title) {
        Book book = bookRepository.findByTitle(title);
        return new BookDTO(
            book.getTitle(),
            book.getEditionType(),
            book.getAgeRestriction(),
            book.getPrice()
        );
    }

    @Override
    public int addCopiesToBooksReleasedAfter(String date, int numberOfCopies) {
        Map<String, Integer> monthsMap = new HashMap<>();
        monthsMap.put("Jan", 1);
        monthsMap.put("Feb", 2);
        monthsMap.put("Mar", 3);
        monthsMap.put("Apr", 4);
        monthsMap.put("May", 5);
        monthsMap.put("Jun", 6);
        monthsMap.put("Jul", 7);
        monthsMap.put("Aug", 8);
        monthsMap.put("Sep", 9);
        monthsMap.put("Oct", 10);
        monthsMap.put("Nov", 11);
        monthsMap.put("Dec", 12);

        Set<Book> books = bookRepository
            .findAllBooksByReleaseDateAfter(
                LocalDate.of(
                    Integer.parseInt(date.split(" ")[2]),
                    monthsMap.get(date.split(" ")[1]),
                    Integer.parseInt(date.split(" ")[0])
            ));

        for (Book book : books) {
            book.setCopies(book.getCopies() + numberOfCopies);
            bookRepository.save(book);
        }

        return books.size() * numberOfCopies;
    }

    @Override
    public int deleteAllBooksByNumberOfCopiesLessThan(int numberOfCopies) {
        return bookRepository.deleteAllByCopiesLessThan(numberOfCopies);
    }

    @Override
    @Transactional
    public int getNumberOfBooksForAnAuthor(String firstName, String lastName) {
        return bookRepository.callGetAuthorsNumberOfBooks(firstName, lastName);
    }
}
