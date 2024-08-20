package bg.softuni.springdataintro.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User extends Base {

    @Column(unique = true)
    private String username;

    @Column
    private int age;

    @OneToMany(targetEntity = Account.class, mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Account> accounts;

    public User(String username, int age) {
        this.username = username;
        this.age = age;
        this.accounts = new HashSet<>();
    }
}
