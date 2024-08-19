package bg.softuni.advquerying.services.interfaces;

import bg.softuni.advquerying.entities.Ingredient;

import java.util.Collection;
import java.util.Set;


public interface IngredientServiceI {

    Set<Ingredient> getAllIngredientsWithNameStartingWith(String start);

    Set<Ingredient> getAllIngredientsWithNameInCollection(Collection<String> namesCollection);

    void deleteIngredientsByName(String name);

    void raiseIngredientsPriceBy10Percent();

    void raiseCertainIngredientsPriceByPercentage(Collection<String> names, double percentage);
}
