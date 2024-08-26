package bg.softuni.jsonprocessingexercise.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User extends Base {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(min = 3)
    private String lastName;

    @Column
    private Integer age;

    @ManyToMany
    @JoinTable(
        name = "users_friends",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id")
    )
    private Set<User> friends;

    @OneToMany(targetEntity = Product.class, mappedBy = "buyer", fetch = FetchType.EAGER)
    private Set<Product> boughtProducts;

    @OneToMany(targetEntity = Product.class, mappedBy = "seller", fetch = FetchType.EAGER)
    private Set<Product> soldProducts;
}
