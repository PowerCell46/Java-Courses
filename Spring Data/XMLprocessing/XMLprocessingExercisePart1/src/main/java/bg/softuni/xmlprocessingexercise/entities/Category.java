package bg.softuni.xmlprocessingexercise.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "categories")
public class Category extends NameBase {

    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    Set<Product> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(products, category.products);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(products);
    }
}
