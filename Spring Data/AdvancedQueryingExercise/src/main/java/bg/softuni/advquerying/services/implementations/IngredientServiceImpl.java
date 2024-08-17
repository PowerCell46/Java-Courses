package bg.softuni.advquerying.services.implementations;

import bg.softuni.advquerying.entities.Ingredient;
import bg.softuni.advquerying.repositories.IngredientRepository;
import bg.softuni.advquerying.services.interfaces.IngredientServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientServiceI {
    private final IngredientRepository ingredientRepository;


    @Override
    public Set<Ingredient> getAllIngredientsWithNameStartingWith(String start) {
        return ingredientRepository.findAllByNameStartsWith(start);
    }

    @Override
    public Set<Ingredient> getAllIngredientsWithNameInCollection(Collection<String> namesCollection) {
        return ingredientRepository.findByNameIn(namesCollection);
    }

    @Override
    @Transactional
    public void deleteIngredientsByName(String name) {
        ingredientRepository.deleteAllByName(name);
    }

    @Override
    @Transactional
    public void raiseIngredientsPriceBy10Percent() {
        ingredientRepository.updateAllWithPrice10PercentUp();
    }

    @Override
    public void raiseCertainIngredientsPriceByPercentage(Collection<String> names, double percentage) {
        if (percentage <= 0) throw new IllegalArgumentException("Invalid percentage entered!");

        ingredientRepository.updatePriceWithPercentageByNameIn(names, percentage);
    }
}
