package bg.softuni.springdataintroexercise;

import bg.softuni.springdataintroexercise.entities.*;
import bg.softuni.springdataintroexercise.repositories.*;
import bg.softuni.springdataintroexercise.services.interfaces.AuthorService;
import bg.softuni.springdataintroexercise.services.interfaces.BookService;
import bg.softuni.springdataintroexercise.services.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final TownRepository townRepository;
    private final AlbumRepository albumRepository;
    private final PictureRepository pictureRepository;

    @Override
    public void run(String... args) throws Exception {
//        seedData();

//        for (Book book : this.bookService.getAllBooksAfter2000()) {
//            System.out.println(book.getTitle());
//        }


//        for (Author author : this.authorService.getAllAuthorsWithAtLeastOneBookBefore1990()) {
//            System.out.println(author.getFirstName() + " " + author.getLastName());
//        }

//        for (Object[] currentData : authorService.getAllAuthorsWithTheirNumberOfBooks()) {
//            Author currentAuthor = (Author) currentData[0];
//            long currentNumberOfBooks = (long) currentData[1];
//
//            System.out.println(
//                currentAuthor.getFirstName() + " " +
//                currentAuthor.getLastName() + ", number of books: " +
//                currentNumberOfBooks
//            );
//        }

//        for (Book book : this.bookService.getAllGeorgePowellBooksOrderedByReleaseDateDescTitleAsc()) {
//            System.out.println(book.getTitle() + ", " + book.getReleaseDate() + ", " + book.getCopies());
//        }


//        Country country = new Country("England");
//        countryRepository.save(country);
//
//
//        Town town = new Town("London", country);
//        townRepository.save(town);
//
//
//        User user = new User("Zorbak", "Stiliyan", "Nikolov", "Stilitoo3%", "stiliyan.nikolov@gmail.com", LocalDate.now(), LocalDate.now(), 21, false, town, town, Set.of(userRepository.findById(2).get(), userRepository.findById(3).get()));
//
//        userRepository.save(user);

//        Album album = new Album("First Album", "grey", true, userRepository.findById(2).get());
//
//        albumRepository.save(album);
//
//        Picture picture1 = new Picture("First Picture", "This is my first posted picture!", "C://disk/local/pic.jpg", Set.of(album), userRepository.findById(2).get());
//
//        pictureRepository.save(picture1);

//        Picture picture2 = new Picture("Second Picture", "This is my second picture", "D://drive/local/picture2.jpg", Set.of(albumRepository.findById(1L).get()), userRepository.findById(2).get());
//
//        pictureRepository.save(picture2);

        //        SELECT
        //            albums.name,
        //            albums.background_color,
        //            pictures.title,
        //            pictures.caption,
        //            pictures.path,
        //            users.username,
        //            users.first_name,
        //            users.last_name
        //        FROM
        //            albums
        //        JOIN
        //            pictures_albums ON
        //                albums.id = pictures_albums.album_id
        //        JOIN pictures ON
        //            pictures_albums.picture_id = pictures.id
        //        JOIN users ON
        //            albums.creator_id = users.id;
    }

    private void seedData() throws IOException {
        System.out.println("SEEDING");
        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();
    }
}
