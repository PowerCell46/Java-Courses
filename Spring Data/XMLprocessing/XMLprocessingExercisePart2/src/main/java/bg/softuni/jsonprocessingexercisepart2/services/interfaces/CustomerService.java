package bg.softuni.jsonprocessingexercisepart2.services.interfaces;


import bg.softuni.jsonprocessingexercisepart2.entities.Customer;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;


public interface CustomerService {

    void seedCustomers() throws IOException, JAXBException;

    long findLastId();

    Customer findById(long id);

    boolean exportToXMLCustomersOrderedByBirthdateAndIsNotYoungDriver();

    boolean exportToJSONCustomerStatistics();
}
