package bg.softuni.jsonprocessingexercisepart2.services.interfaces;

import bg.softuni.jsonprocessingexercisepart2.entities.Car;
import bg.softuni.jsonprocessingexercisepart2.entities.Supplier;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;


public interface CarService {

    void seedCars() throws IOException, JAXBException;

    long findLastId();

    Car findById(long id);

    boolean exportToXMLCarsByToyotaMakerOrderedModelAscTravelledDistanceDesc();

    boolean exportToXMLCarsAndTheirParts();
}
