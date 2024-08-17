package bg.softuni.advquerying.services.interfaces;

import bg.softuni.advquerying.entities.Shampoo;
import bg.softuni.advquerying.entities.Size;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;


public interface ShampooServiceI {

    Set<Shampoo> findAllShampoosBySizeOrderedById(Size size);

    Set<Shampoo> findAllShampoosByLabelOrLabelId(Size size, long labelId);

    Set<Shampoo> findAllShampoosByPriceGreaterThan(double price);

    Integer getNumberOfShampoosCheaperThan(double price);

    Set<Shampoo> findAllByCertainIngredientNames(Collection<String> ingredientNames);

    Set<Shampoo> findAllByNumberOfIngredientsLowerThan(Integer numberOfIngredients);
}
