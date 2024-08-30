package bg.softuni.jsonprocessingexercisepart2.services.impls;

import bg.softuni.jsonprocessingexercisepart2.DTOs.exports.CarDataExportDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.exports.PartDataExportDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.queries.*;
import bg.softuni.jsonprocessingexercisepart2.DTOs.seedings.CarRootSeedDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.seedings.CarSeedDTO;
import bg.softuni.jsonprocessingexercisepart2.entities.Car;
import bg.softuni.jsonprocessingexercisepart2.entities.Part;
import bg.softuni.jsonprocessingexercisepart2.repositories.CarRepository;
import bg.softuni.jsonprocessingexercisepart2.services.interfaces.CarService;
import bg.softuni.jsonprocessingexercisepart2.services.interfaces.PartService;
import com.google.gson.Gson;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final PartService partService;
    private static final String FILE_PATH = "src/main/resources/static/XML/cars.xml";

    private final Gson gson;

    @Override
    public void seedCars() throws JAXBException {
        if (carRepository.count() > 0) return;

        long partLastId = partService.findLastId();

        JAXBContext jaxbContext = JAXBContext.newInstance(CarRootSeedDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        CarRootSeedDTO carRootSeedDTO = (CarRootSeedDTO) unmarshaller.unmarshal(new File(FILE_PATH));

        carRootSeedDTO
            .getCars()
            .stream()
            .forEach(carSeedDTO ->
                carRepository.saveAndFlush(
                    mapCarSeedDTOtoCarEntity(carSeedDTO, partLastId)
                )
            );
    }

    @Override
    public long findLastId() {
        return carRepository.count();
    }

    @Override
    public Car findById(long id) {
        return carRepository.findById(id).get();
    }

    @Override
    public boolean exportToXMLCarsByToyotaMakerOrderedModelAscTravelledDistanceDesc() {
        String EXPORT_RESULT_XML_FILE_PATH =
            "src/main/resources/static/resultsXML/toyotaCarsOrderedByModelAscTravelledDistanceDesc.xml";

        Set<Car> toyotaCars = carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota");

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CarRootQueryDTO.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(
                new CarRootQueryDTO(toyotaCars
                    .stream()
                    .map(car -> mapCarEntityToCarQueryDTO(car)).toList()),
                new File(EXPORT_RESULT_XML_FILE_PATH));


            System.out.println("Successful operation!");
            return true;

        } catch (JAXBException e) {
            System.out.println("An Error occurred while Writing the Data!");
            return false;
        }
    }

    @Override
    public boolean exportToXMLCarsAndTheirParts() {
         String EXPORT_RESULT_XML_FILE_PATH =
            "src/main/resources/static/resultsXML/carsAndTheirParts.xml";

        List<Car> cars = carRepository.findAll();

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CarRootSecondQueryDTO.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(new CarRootSecondQueryDTO(
                cars
                .stream()
                .map(car ->
                    mapCarEntityToCarSecondQueryDTO(car)).toList()),
                new File(EXPORT_RESULT_XML_FILE_PATH)
            );

            System.out.println("Successful operation!");
            return true;

        } catch (JAXBException e) {
            System.out.println("An Error occurred while Writing the Data!");
            return false;
        }
    }

    private Car mapCarSeedDTOtoCarEntity(CarSeedDTO carSeedDTO, long lastPartId) {
        Set<Part> parts = new HashSet<>();
        Set<String> partNames = new HashSet<>();
        int numberOfGeneratedParts = new Random().nextInt(3, 6);

        for (int i = 0; i < numberOfGeneratedParts; i++) {
            Part partEntity = partService.findById(new Random().nextLong(1, lastPartId + 1));
            if (partNames.contains(partEntity.getName())) continue;

            partNames.add(partEntity.getName());
            parts.add(partEntity);
        }

        return new Car(
            carSeedDTO.getMake(),
            carSeedDTO.getModel(),
            carSeedDTO.getTravelledDistance(),
            parts,
            Set.of()
        );
    }

    private CarQueryDTO mapCarEntityToCarQueryDTO(Car car) {
        return CarQueryDTO
            .builder()
            .id(car.getId())
            .make(car.getMake())
            .model(car.getModel())
            .travelledDistance(car.getTravelledDistance())
            .build();
    }

    private CarSecondQueryDTO mapCarEntityToCarSecondQueryDTO(Car car) {
        return CarSecondQueryDTO
                .builder()
                .make(car.getMake())
                .model(car.getModel())
                .travelledDistance(car.getTravelledDistance())
                .parts(mapPartEntityToCarPartSecondQueryDTO(car.getParts()))
                .build();
    }

    private List<CarPartSecondQueryDTO> mapPartEntityToCarPartSecondQueryDTO(Set<Part> parts) {
        List<CarPartSecondQueryDTO> result = new ArrayList<>();
        for (Part part : parts) {
            result.add(CarPartSecondQueryDTO
                .builder()
                .name(part.getName())
                .price(part.getPrice())
                .build());
        }
        return result;
    }
}
