package bg.softuni.advquerying.repositories;

import bg.softuni.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;


public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Set<Ingredient> findAllByNameStartsWith(String startName);

    Set<Ingredient> findByNameIn(Collection<String> names);

    void deleteAllByName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Ingredient i SET i.price = i.price * 1.1")
    void updateAllWithPrice10PercentUp();

    @Modifying
    @Transactional
    @Query("UPDATE Ingredient i " +
        "SET i.price = i.price + (i.price / 100  * :percentage) " +
        "WHERE i.name IN (:names)")
    void updatePriceWithPercentageByNameIn(Collection<String> names, double percentage);
}
