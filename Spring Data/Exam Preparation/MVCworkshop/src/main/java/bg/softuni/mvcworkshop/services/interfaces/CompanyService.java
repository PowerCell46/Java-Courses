package bg.softuni.mvcworkshop.services.interfaces;


import bg.softuni.mvcworkshop.entities.Company;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.util.Optional;

public interface CompanyService {

    long findCount();

    String getXMLdata() throws IOException;

    boolean seedXMLdata(String companiesData) throws JAXBException;

    Optional<Company> findByName(String name);
}
