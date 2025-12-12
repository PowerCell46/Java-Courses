package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_username_unique", columnList = "username", unique = true)
        }
)
@Getter
@Setter
@SQLDelete(sql = "UPDATE producers SET is_deleted = true WHERE id = ?")
@AllArgsConstructor
@NoArgsConstructor
public class User extends CommonEntity {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public User(String username, String password) {
        super();

        this.username = username;
        this.password = password;
        this.roles = new HashSet<>();
    }
}
