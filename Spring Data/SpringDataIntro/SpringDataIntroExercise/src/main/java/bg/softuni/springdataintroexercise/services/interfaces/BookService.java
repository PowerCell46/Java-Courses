package bg.softuni.springdataintroexercise.services.interfaces;

import bg.softuni.springdataintroexercise.entities.Book;

import java.io.IOException;
import java.util.Set;

public interface BookService {

    void seedBooks() throws IOException;

    Set<Book> getAllBooksAfter2000();

    Set<Book> getAllGeorgePowellBooksOrderedByReleaseDateDescTitleAsc();
}
