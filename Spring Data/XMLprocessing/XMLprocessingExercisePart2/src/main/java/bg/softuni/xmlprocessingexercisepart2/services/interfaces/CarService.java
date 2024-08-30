package bg.softuni.xmlprocessingexercisepart2.services.interfaces;

import bg.softuni.xmlprocessingexercisepart2.entities.Car;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;


public interface CarService {

    void seedCars() throws IOException, JAXBException;

    long findLastId();

    Car findById(long id);

    boolean exportToXMLCarsByToyotaMakerOrderedModelAscTravelledDistanceDesc();

    boolean exportToXMLCarsAndTheirParts();
}
