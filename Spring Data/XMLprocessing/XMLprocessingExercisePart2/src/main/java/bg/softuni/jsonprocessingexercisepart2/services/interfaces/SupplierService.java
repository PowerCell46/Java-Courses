package bg.softuni.jsonprocessingexercisepart2.services.interfaces;


import bg.softuni.jsonprocessingexercisepart2.entities.Supplier;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;


public interface SupplierService {

    void seedSuppliers() throws IOException, JAXBException;

    long findLastId();

    Supplier findById(long id);

    boolean exportToXMLSuppliersWithPartsNotFromAbroad();
}
