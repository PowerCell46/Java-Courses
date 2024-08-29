package bg.softuni.mvcworkshop.services.interfaces;

import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public interface EmployeeService {

    long findCount();

    String getXMLdata() throws IOException;

    boolean seedXMLdata(String employees) throws JAXBException;

    String getOlderThan25();
}
