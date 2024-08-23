package bg.softuni.springdataintroexercise.services.impls;

import bg.softuni.springdataintroexercise.entities.Author;
import bg.softuni.springdataintroexercise.repositories.AuthorRepository;
import bg.softuni.springdataintroexercise.services.interfaces.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private static final String AUTHORS_FILE_PATH = "src/main/resources/files/authors.txt";


    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() == 0) {
            Files.readAllLines(Path.of(AUTHORS_FILE_PATH)).stream().filter(row -> !row.isEmpty())
                .forEach(row -> {
                    String[] data = row.split("\\s+");
                    this.authorRepository.saveAndFlush(new Author(data[0], data[1]));
                });
        }
    }

    @Override
    public Author getRandomAuthor() {
        Optional<Author> optionalAuthor = this.authorRepository
                .findById(new Random().nextLong(0L, this.authorRepository.count() + 1L));

        return optionalAuthor.orElse(null);
    }

    @Override
    public Set<Author> getAllAuthorsWithAtLeastOneBookBefore1990() {
        return authorRepository.findAuthorsWithBooksReleasedBefore(LocalDate.of(1990, 1, 1));
    }

    @Override
    public Set<Object[]> getAllAuthorsWithTheirNumberOfBooks() {
        return this.authorRepository.getAllAuthorsWithNumberOfBooks();
    }

    @Override
    public Author findByFullName(String fullName) {
        String firstName = fullName.split(" ")[0];
        String lastName = fullName.split(" ")[1];
        return this.authorRepository.findByFirstNameAndLastName(firstName, lastName);
    }
}
