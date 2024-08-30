package bg.softuni.xmlprocessingexercisepart2.services.interfaces;


import bg.softuni.xmlprocessingexercisepart2.entities.Supplier;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;


public interface SupplierService {

    void seedSuppliers() throws IOException, JAXBException;

    long findLastId();

    Supplier findById(long id);

    boolean exportToXMLSuppliersWithPartsNotFromAbroad();
}
