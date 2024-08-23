package bg.softuni.springdataintroexercise.services.impls;

import bg.softuni.springdataintroexercise.entities.Author;
import bg.softuni.springdataintroexercise.entities.Book;
import bg.softuni.springdataintroexercise.entities.Category;
import bg.softuni.springdataintroexercise.entities.enums.AgeRestriction;
import bg.softuni.springdataintroexercise.entities.enums.EditionType;
import bg.softuni.springdataintroexercise.repositories.BookRepository;
import bg.softuni.springdataintroexercise.repositories.CategoryRepository;
import bg.softuni.springdataintroexercise.services.interfaces.AuthorService;
import bg.softuni.springdataintroexercise.services.interfaces.BookService;
import bg.softuni.springdataintroexercise.services.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() == 0) {
            Files.readAllLines(Path.of(BOOKS_FILE_PATH)).stream().filter(row -> !row.isEmpty())
            .forEach(row -> {
                String[] data = row.split("\\s+");

                Author author = authorService.getRandomAuthor();
                EditionType editionType = EditionType.values()[Integer.parseInt(data[0])];
                LocalDate releaseDate = LocalDate.parse(data[1],
                                                 DateTimeFormatter.ofPattern("d/M/yyyy"));
                int copies = Integer.parseInt(data[2]);
                BigDecimal price = new BigDecimal(data[3]);
                AgeRestriction ageRestriction = AgeRestriction
                                                .values()[Integer.parseInt(data[4])];
                String title = Arrays.stream(data)
                        .skip(5)
                        .collect(Collectors.joining(" "));
                Set<Category> categories = categoryService.getRandomCategories();


                Book book = new Book(title, editionType, price, copies, releaseDate,
                        ageRestriction, author, categories);

                bookRepository.save(book);
            });
        }
    }

    @Override
    public Set<Book> getAllBooksAfter2000() {
        return this.bookRepository.findAllByReleaseDateAfter(LocalDate.of(2000, 1, 1));
    }

    @Override
    public Set<Book> getAllGeorgePowellBooksOrderedByReleaseDateDescTitleAsc() {
        return this.bookRepository
            .getAllByAuthorOrderByReleaseDateDescAndTitleAsc(
                this.authorService.findByFullName("George Powell")
            );
    }
}
