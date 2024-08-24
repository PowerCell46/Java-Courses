package bg.softuni.springdataadvancedqueryingexercise;

import bg.softuni.springdataadvancedqueryingexercise.entities.Author;
import bg.softuni.springdataadvancedqueryingexercise.entities.Book;
import bg.softuni.springdataadvancedqueryingexercise.services.interfaces.AuthorService;
import bg.softuni.springdataadvancedqueryingexercise.services.interfaces.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Main implements CommandLineRunner {
    private final BookService bookService;
    private final AuthorService authorService;

    @Override
    public void run(String... args) throws Exception {
//        for (Book book : bookService.findAllBooksByAgeRestrictionCaseInsensitive("miNor")) {
//            System.out.println(book.getTitle());
//        }

//        for (Book book : bookService.findAllGoldenEditionBooksWithLessThan5000Copies()) {
//            System.out.println(book.getTitle());
//            System.out.println(book.getEditionType());
//            System.out.println(book.getCopies());
//            System.out.println("------------");
//        }

//        for (Book book : bookService.findAllBooksCheaperThan5OrMoreExpensiveThan40()) {
//            System.out.println(book.getTitle());
//            System.out.println(book.getPrice());
//            System.out.println("------------");
//        }

//        for (Book book : bookService.findAllBooksByReleaseYearDifferentThan(2000)) {
//            System.out.println(book.getTitle());
//            System.out.println(book.getReleaseDate());
//            System.out.println("------------");
//        }

//        for (Book book : bookService.findAllBooksByReleaseDateBefore("12-04-1992")) {
//            System.out.println(book.getTitle());
//            System.out.println(book.getReleaseDate());
//            System.out.println("------------");
//        }

//        for (Author author : authorService.findAllByFirstNameEndingWith("dy")) {
//            System.out.println(author.getFirstName() + " " + author.getLastName());
//        }

//        for (Book book : bookService.findAllBooksByTitleContainingCaseInsensitive("sK")) {
//            System.out.println(book.getTitle());
//            System.out.println("--------");
//        }

//        for (Book book : bookService.findAllBooksWithAuthorLastNameStartingWith("Ric")) {
//            System.out.println(book.getTitle());
//            System.out.println(book.getAuthor().getFirstName() + book.getAuthor().getLastName());
//            System.out.println("---------");
//        }

//        System.out.println(String.format(
//                "There are %d books with longer titles than %d symbols",
//                bookService.getNumberOfBooksWithTitleLengthMoreThan(40),
//                40
//            )
//        );

//        for (Object[] currentData : authorService.getAllAuthorsWithTheirNumberOfBooks()) {
//            System.out.format("%s %s - %d\n",
//                    ((Author) currentData[0]).getFirstName(),
//                    ((Author) currentData[0]).getLastName(),
//                    ((long) currentData[1])
//            );
//        }

//        System.out.println(bookService.getBookByTitle("Things Fall Apart"));

//        System.out.println(bookService.addCopiesToBooksReleasedAfter("12 Oct 2005", 100));

//        System.out.println(bookService.deleteAllBooksByNumberOfCopiesLessThan(210));

//        System.out.println(bookService.getNumberOfBooksForAnAuthor("Jane", "Ortiz"));
    }
}
