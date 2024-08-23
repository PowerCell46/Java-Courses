package bg.softuni.springdataintroexercise.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category extends Base {

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "categories")
    Set<Book> books;

    public Category(String name) {
        this.name = name;
        this.books = new HashSet<>();
    }

    public Category() {}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Category category = (Category) obj;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
