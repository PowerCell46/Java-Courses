package bg.softuni.springdataintroexercise.entities;


import bg.softuni.springdataintroexercise.entities.enums.AgeRestriction;
import bg.softuni.springdataintroexercise.entities.enums.EditionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "books")
@Getter
@Setter
public class Book extends Base {

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
//    @Size(min = 1, max = 50, message = "Title must be between 1 and 50 characters")
    private String title;


    @Column(columnDefinition = "VARCHAR(1000)", nullable = true)
    private String description;

    @Column(name = "edition_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private EditionType editionType;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int copies;

    @Column(name = "release_date", nullable = true)
    private LocalDate releaseDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "age_restriction", nullable = false)
    private AgeRestriction ageRestriction;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @ManyToMany
    @JoinTable(name = "books_categories",
        joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private Set<Category> categories;

    public Book(String title, EditionType editionType, BigDecimal price, int copies, LocalDate releaseDate, AgeRestriction ageRestriction, Author author, Set<Category> categories) {
        this.title = title;
        this.editionType = editionType;
        this.price = price;
        this.copies = copies;
        this.releaseDate = releaseDate;
        this.ageRestriction = ageRestriction;
        this.author = author;
        this.categories = categories;
    }

    public Book() {}
}
