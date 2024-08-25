package bg.softuni.jsonprocessingexercise;

import bg.softuni.jsonprocessingexercise.services.interfaces.CategoryService;
import bg.softuni.jsonprocessingexercise.services.interfaces.ProductService;
import bg.softuni.jsonprocessingexercise.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        categoryService.seedCategories();
        userService.seedUsers();
        productService.seedProducts();

//        boolean result = productService.exportToJSONProductsWithoutSellerWithPriceBetween500And1000();
//        boolean result = userService.exportToJSONUsersWithAtLeastOneSellingItemWithBuyer();
        boolean result = categoryService.exportToJSONCategoriesStatistics();

        System.out.println(result);
    }
}
