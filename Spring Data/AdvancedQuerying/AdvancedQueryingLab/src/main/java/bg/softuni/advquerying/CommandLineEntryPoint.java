package bg.softuni.advquerying;

import bg.softuni.advquerying.entities.Ingredient;
import bg.softuni.advquerying.entities.Shampoo;
import bg.softuni.advquerying.entities.Size;
import bg.softuni.advquerying.services.interfaces.IngredientServiceI;
import bg.softuni.advquerying.services.interfaces.ShampooServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


@Component
@RequiredArgsConstructor
public class CommandLineEntryPoint implements CommandLineRunner {
    private final ShampooServiceI shampooService;
    private final IngredientServiceI ingredientService;

    @Override
    public void run(String... args) throws Exception {

//        Set<Shampoo> shampoos = this.shampooService.findAllShampoosBySizeOrderedById(Size.MEDIUM);

//        Set<Shampoo> shampoos = this.shampooService.findAllShampoosByLabelOrLabelId(Size.MEDIUM, 10L);

//        Set<Shampoo> shampoos = this.shampooService.findAllShampoosByPriceGreaterThan(5);
//
//        for (Shampoo shampoo : shampoos) {
//            System.out.println(shampoo);
//        }

//        Set<Ingredient> ingredients = ingredientService.getAllIngredientsWithNameStartingWith("M");
//
//        for (Ingredient ingredient : ingredients) {
//            System.out.println(ingredient.getName());
//        }

//        Set<Ingredient> ingredients = ingredientService.getAllIngredientsWithNameInCollection(List.of("Lavender", "Apple", "Herbs"));
//
//        for (Ingredient ingredient : ingredients) {
//            System.out.println(ingredient.getName());
//        }

//        System.out.println(shampooService.getNumberOfShampoosCheaperThan(8.50));

//        Set<Shampoo> shampoos = shampooService.findAllByCertainIngredientNames(List.of("Berry", "Mineral-Collagen"));
//
//        for (Shampoo shampoo : shampoos) {
//            System.out.println(shampoo.getBrand());
//        }

//        Set<Shampoo> shampoos = shampooService.findAllByNumberOfIngredientsLowerThan(2);
//
//        for (Shampoo shampoo : shampoos) {
//            System.out.println(shampoo.getBrand());
//        }

//        ingredientService.deleteIngredientsByName("Active-Caffeine");

//        ingredientService.raiseIngredientsPriceBy10Percent();

//        ingredientService.raiseCertainIngredientsPriceByPercentage(List.of("Mineral-Collagen", "Cherry"), 50);

    }
}
