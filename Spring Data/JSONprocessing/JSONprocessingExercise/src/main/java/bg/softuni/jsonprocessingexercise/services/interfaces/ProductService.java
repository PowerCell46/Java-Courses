package bg.softuni.jsonprocessingexercise.services.interfaces;


import bg.softuni.jsonprocessingexercise.DTOs.ProductQueryDTO;

import java.io.IOException;
import java.util.Set;

public interface ProductService {

    void seedProducts() throws IOException;

    boolean exportToJSONProductsWithoutSellerWithPriceBetween500And1000();
}
