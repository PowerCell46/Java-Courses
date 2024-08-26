package bg.softuni.jsonprocessingexercisepart2.services.interfaces;


import bg.softuni.jsonprocessingexercisepart2.entities.Part;

import java.io.IOException;


public interface PartService {

    void seedParts() throws IOException;

    long findLastId();

    Part findById(long id);
}
