package bg.softuni.jsonprocessingexercisepart2.services.interfaces;


import bg.softuni.jsonprocessingexercisepart2.entities.Customer;

import java.io.IOException;


public interface CustomerService {

    void seedCustomers() throws IOException;

    long findLastId();

    Customer findById(long id);

    boolean exportToJSONCustomersOrderedByBirthdateAndIsNotYoungDriver();

    boolean exportToJSONCustomerStatistics();
}
