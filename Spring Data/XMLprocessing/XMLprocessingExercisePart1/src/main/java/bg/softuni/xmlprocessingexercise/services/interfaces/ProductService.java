package bg.softuni.xmlprocessingexercise.services.interfaces;


import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public interface ProductService {

    void seedProducts() throws JAXBException;

    boolean exportToXMLProductsWithoutSellerWithPriceBetween500And1000();
}
