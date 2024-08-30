package bg.softuni.jsonprocessingexercise.services.impls;

import bg.softuni.jsonprocessingexercise.DTOs.ProductQueryDTO;
import bg.softuni.jsonprocessingexercise.DTOs.ProductSeedDTO;
import bg.softuni.jsonprocessingexercise.entities.Category;
import bg.softuni.jsonprocessingexercise.entities.Product;
import bg.softuni.jsonprocessingexercise.repositories.ProductRepository;
import bg.softuni.jsonprocessingexercise.services.interfaces.CategoryService;
import bg.softuni.jsonprocessingexercise.services.interfaces.ProductService;
import bg.softuni.jsonprocessingexercise.services.interfaces.UserService;
import bg.softuni.jsonprocessingexercise.utils.ValidationUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private static final String FILE_PATH = "src/main/resources/static/JSON/products.json";

    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Override
    public void seedProducts() throws IOException {
        if (this.productRepository.count() > 0) return;

        long finalPossibleUserId = userService.getLastPossibleId();
        long finalPossibleCategoryId = categoryService.getLastPossibleId();

        String jsonData = new String(Files.readAllBytes(Path.of(FILE_PATH)));

        ProductSeedDTO[] productSeedDTOS = gson.fromJson(jsonData, ProductSeedDTO[].class);

        for (ProductSeedDTO productSeedDTO : productSeedDTOS) {
            if (validationUtil.isValid(productSeedDTO)) {
                Product product = addRandomBuyerSellerCategories(
                    productSeedDTO,
                    finalPossibleUserId,
                    finalPossibleCategoryId
                );
                productRepository.saveAndFlush(product);

            } else {
                validationUtil
                    .getViolations(productSeedDTO)
                    .forEach(err -> System.out.println(err.getMessage()));
            }
        }

    }

    @Override
    public boolean exportToJSONProductsWithoutSellerWithPriceBetween500And1000() {
        String EXPORT_RESULT_JSON_FILE_PATH =
            "src/main/resources/static/resultJSON/productsWithoutBuyerWithPriceBetween500And1000.json";

        Set<Product> products = productRepository.findAllByBuyerIsNullAndPriceBetween(
            BigDecimal.valueOf(500),
            BigDecimal.valueOf(1_000)
        );

        try (FileWriter writer = new FileWriter(EXPORT_RESULT_JSON_FILE_PATH)) {
            List<String> jsonObjectsResult = new ArrayList<>();
            for (Product product : products) {
                ProductQueryDTO productQueryDTO = mapProductEntityToProductQueryDTO(product);

                String json = gson.toJson(productQueryDTO);
                jsonObjectsResult.add(json);
            }

            writer.write(String.valueOf(jsonObjectsResult));
            writer.write(System.lineSeparator());

            System.out.println("Successful operation!");
            return true;

        } catch (IOException e) {
            System.out.println("An Error occurred while Writing the Data!");
            return false;
        }
    }

    protected Product addRandomBuyerSellerCategories(ProductSeedDTO productSeedDTO,
        long finalPossibleUserId, long finalPossibleCategoryId
    ) {
        Product product = modelMapper.map(productSeedDTO, Product.class);

        Random random = new Random();

        long sellerId = random.nextLong(1, finalPossibleUserId + 1);
        product.setSeller(userService.findById(sellerId));

        Long buyerId = sellerId;
        while (buyerId == sellerId) buyerId = random.nextLong(0, finalPossibleUserId + 1);
        if (buyerId == 0 || buyerId % 7 == 0) buyerId = null;
        if (buyerId != null) product.setBuyer(userService.findById(buyerId));


        int numberOfCategories = random.nextInt(1, 4);
        Set<Category> categories = new HashSet<>();
        List<Long> categoriesId = new LinkedList<>();

        for (int i = 0; i < numberOfCategories + 1; i++) {
            long currentGeneratedId = random.nextLong(1, finalPossibleCategoryId + 1);
            if (categoriesId.contains(currentGeneratedId)) continue;

            categoriesId.add(currentGeneratedId);
            categories.add(categoryService.findById(currentGeneratedId));
        }
        product.setCategories(categories);

        return product;
    }

    private static ProductQueryDTO mapProductEntityToProductQueryDTO(Product product) {
            StringBuilder sellerName = new StringBuilder();
            if (product.getSeller().getFirstName() != null) {
                sellerName.append(product.getSeller().getFirstName()).append(" ");
            }
            sellerName.append(product.getSeller().getLastName());

            return ProductQueryDTO
                .builder()
                .name(product.getName())
                .price(product.getPrice())
                .seller(sellerName.toString())
                .build();
    }
}
