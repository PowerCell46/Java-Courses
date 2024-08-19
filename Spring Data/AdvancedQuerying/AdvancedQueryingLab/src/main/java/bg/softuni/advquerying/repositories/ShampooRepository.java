package bg.softuni.advquerying.repositories;

import bg.softuni.advquerying.entities.Label;
import bg.softuni.advquerying.entities.Shampoo;
import bg.softuni.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;


@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {
    Set<Shampoo> findAllBySizeOrderById(Size size);

    Set<Shampoo> findAllBySizeOrLabelOrderByPrice(Size size, Label label); // all with given size united with all with given label

    Set<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    Integer countByPriceIsLessThan(BigDecimal price);

    @Query("SELECT DISTINCT s " +
       "FROM Shampoo s " +
       "JOIN s.ingredients i " +
       "WHERE i.name IN :ingredientNames")
    Set<Shampoo> findAllByIngredientsIn(Collection<String> ingredientNames);

    @Query("SELECT s " +
        "FROM Shampoo s " +
        "JOIN s.ingredients i " +
        "GROUP BY s.id " +
        "HAVING COUNT(i.id) < :numberOfIngredients")
    Set<Shampoo> findAllByNumberOfIngredientsLowerThan(Integer numberOfIngredients);
}
