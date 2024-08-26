package bg.softuni.jsonprocessingexercisepart2.services.interfaces;


import bg.softuni.jsonprocessingexercisepart2.entities.Supplier;

import java.io.IOException;


public interface SupplierService {

    void seedSuppliers() throws IOException;

    long findLastId();

    Supplier findById(long id);

    boolean exportToJSONSuppliersWithPartsNotFromAbroad();
}
