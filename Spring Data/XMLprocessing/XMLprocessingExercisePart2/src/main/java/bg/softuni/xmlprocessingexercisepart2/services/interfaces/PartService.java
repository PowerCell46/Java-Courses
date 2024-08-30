package bg.softuni.xmlprocessingexercisepart2.services.interfaces;


import bg.softuni.xmlprocessingexercisepart2.entities.Part;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;


public interface PartService {

    void seedParts() throws IOException, JAXBException;

    long findLastId();

    Part findById(long id);
}
