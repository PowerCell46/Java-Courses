package bg.softuni.entities;

import bg.softuni.orm.annotations.Column;
import bg.softuni.orm.annotations.Entity;
import bg.softuni.orm.annotations.Id;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "users")
public class User {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private int age;

    @Column(name = "registration_timestamp")
    private LocalDate registrationTimestamp;

    public User(String username, String email, int age, LocalDate registrationTimestamp) {
        this.username = username;
        this.email = email;
        this.age = age;
        this.registrationTimestamp = registrationTimestamp;
    }
}
