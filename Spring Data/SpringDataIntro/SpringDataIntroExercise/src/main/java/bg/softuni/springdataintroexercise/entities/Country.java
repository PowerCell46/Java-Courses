package bg.softuni.springdataintroexercise.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "countries")
@NoArgsConstructor
public class Country extends Base {

    private String name;

    @OneToMany(targetEntity = Town.class, mappedBy = "country")
    private Set<Town> towns;

    public Country(String name) {
        this.name = name;
        this.towns = new HashSet<>();
    }
}
