package bg.softuni.springdataadvancedqueryingexercise.services.impls;

import bg.softuni.springdataadvancedqueryingexercise.entities.Author;
import bg.softuni.springdataadvancedqueryingexercise.repositories.AuthorRepository;
import bg.softuni.springdataadvancedqueryingexercise.services.interfaces.AuthorService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public Set<Author> findAllByFirstNameEndingWith(String substring) {
        return authorRepository.findAllByFirstNameEndsWith(substring);
    }

    @Override
    public List<Object[]> getAllAuthorsWithTheirNumberOfBooks() {
        return authorRepository.getAllAuthorsWithTheirNumberOfBooksDesc();
    }
}
