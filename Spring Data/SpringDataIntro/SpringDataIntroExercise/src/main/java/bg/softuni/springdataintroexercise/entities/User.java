package bg.softuni.springdataintroexercise.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    @Size(min = 4)
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Transient
    private String fullName;

    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
    }

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase character!")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase character!")
    @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one digit!")
    @Pattern(regexp = ".*[!@#$%^&*()_+<>?].*", message = "Password must contain at least one special character!")
    private String password;

    @Email
    private String email;

    @Column(name = "registered_on")
    private LocalDate registeredOn;

    @Column(name = "last_time_logged_on")
    private LocalDate lastTimeLoggedOn;

    @Column
    @Min(1)
    @Max(120)
    private int age;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "born_town_id", referencedColumnName = "id")
    private Town bornTown;

    @ManyToOne
    @JoinColumn(name = "current_town_id", referencedColumnName = "id")
    private Town currentTown;

    @ManyToMany
    @JoinTable(name = "friends_list",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    private Set<User> friends;

    @OneToMany(targetEntity = Album.class, mappedBy = "creator")
    private Set<Album> albums;

    @OneToMany(targetEntity = Picture.class, mappedBy = "creator")
    private Set<Picture> pictures;



    public User(String username, String firstName, String lastName, String password, String email, LocalDate registeredOn, LocalDate lastTimeLoggedOn, int age, boolean isDeleted, Town bornTown, Town currentTown) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.registeredOn = registeredOn;
        this.lastTimeLoggedOn = lastTimeLoggedOn;
        this.age = age;
        this.isDeleted = isDeleted;
        this.bornTown = bornTown;
        this.currentTown = currentTown;
    }

    public User(String username, String firstName, String lastName, String password, String email, LocalDate registeredOn, LocalDate lastTimeLoggedOn, int age, boolean isDeleted, Town bornTown, Town currentTown, Set<User> friends, Set<Album> albums, Set<Picture> pictures) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.registeredOn = registeredOn;
        this.lastTimeLoggedOn = lastTimeLoggedOn;
        this.age = age;
        this.isDeleted = isDeleted;
        this.bornTown = bornTown;
        this.currentTown = currentTown;
        this.friends = friends;
        this.albums = albums;
        this.pictures = pictures;
    }
}
