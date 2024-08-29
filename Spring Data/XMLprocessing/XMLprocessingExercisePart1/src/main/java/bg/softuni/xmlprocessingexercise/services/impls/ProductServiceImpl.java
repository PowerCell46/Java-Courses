package bg.softuni.xmlprocessingexercise.services.impls;

import bg.softuni.xmlprocessingexercise.DTOs.querying.ProductQueryDTO;
import bg.softuni.xmlprocessingexercise.DTOs.querying.ProductRootQueryDTO;
import bg.softuni.xmlprocessingexercise.DTOs.seeding.ProductRootSeedDTO;
import bg.softuni.xmlprocessingexercise.DTOs.seeding.ProductSeedDTO;
import bg.softuni.xmlprocessingexercise.entities.Category;
import bg.softuni.xmlprocessingexercise.entities.Product;
import bg.softuni.xmlprocessingexercise.repositories.ProductRepository;
import bg.softuni.xmlprocessingexercise.services.interfaces.CategoryService;
import bg.softuni.xmlprocessingexercise.services.interfaces.ProductService;
import bg.softuni.xmlprocessingexercise.services.interfaces.UserService;
import bg.softuni.xmlprocessingexercise.utils.ValidationUtil;
import com.google.gson.Gson;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private static final String FILE_PATH = "src/main/resources/static/XML/products.xml";

    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Override
    public void seedProducts() throws JAXBException {
        if (this.productRepository.count() > 0) return;

        long finalPossibleUserId = userService.getLastPossibleId();
        long finalPossibleCategoryId = categoryService.getLastPossibleId();

        JAXBContext jaxbContext = JAXBContext.newInstance(ProductRootSeedDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ProductRootSeedDTO productRootSeedDTO = (ProductRootSeedDTO) unmarshaller.unmarshal(new File(FILE_PATH));

        for (ProductSeedDTO productSeedDTO : productRootSeedDTO.getProducts()) {
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
    public boolean exportToXMLProductsWithoutSellerWithPriceBetween500And1000() {
        String EXPORT_RESULT_XML_FILE_PATH =
            "src/main/resources/static/resultXML/productsWithoutBuyerWithPriceBetween500And1000.xml";

        Set<Product> products = productRepository.findAllByBuyerIsNullAndPriceBetween(
            BigDecimal.valueOf(500),
            BigDecimal.valueOf(1_000)
        );

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ProductRootQueryDTO.class);

            ProductRootQueryDTO productRootQueryDTO = new ProductRootQueryDTO(
                products
                    .stream()
                    .map(product ->
                            mapProductEntityToProductQueryDTO(product))
                    .toList());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(productRootQueryDTO, new File(EXPORT_RESULT_XML_FILE_PATH));

            System.out.println("Successful operation!");
            return true;

        } catch (JAXBException e) {
            System.out.println("An Error occurred while Writing or Parsing the Data!");
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
