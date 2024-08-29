package bg.softuni.xmlprocessingexercise;

import bg.softuni.xmlprocessingexercise.services.interfaces.CategoryService;
import bg.softuni.xmlprocessingexercise.services.interfaces.ProductService;
import bg.softuni.xmlprocessingexercise.services.interfaces.UserService;
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
        userService.seedUsers();
        categoryService.seedCategories();
        productService.seedProducts();

//        boolean result = productService.exportToXMLProductsWithoutSellerWithPriceBetween500And1000();
        boolean result = userService.exportToXMLUsersWithAtLeastOneSellingItemWithBuyer();
//        boolean result = categoryService.exportToXMLCategoriesStatistics();
//        boolean result = userService.exportToJSONUsersSellingItemsOrderedByNumOfProductsSoldDescLastNameAsc();

        System.out.println(result);
    }
}
