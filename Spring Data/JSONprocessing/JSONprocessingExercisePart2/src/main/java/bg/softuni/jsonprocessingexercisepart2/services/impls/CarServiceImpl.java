package bg.softuni.jsonprocessingexercisepart2.services.impls;

import bg.softuni.jsonprocessingexercisepart2.DTOs.exports.CarDataExportDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.exports.CarExportDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.exports.PartDataExportDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.seeds.CarSeedDTO;
import bg.softuni.jsonprocessingexercisepart2.entities.Car;
import bg.softuni.jsonprocessingexercisepart2.entities.Part;
import bg.softuni.jsonprocessingexercisepart2.repositories.CarRepository;
import bg.softuni.jsonprocessingexercisepart2.services.interfaces.CarService;
import bg.softuni.jsonprocessingexercisepart2.services.interfaces.PartService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    private static final String FILE_PATH = "src/main/resources/static/JSON/cars.json";

    private final Gson gson;

    @Override
    public void seedCars() throws IOException {
        if (carRepository.count() > 0) return;

        String jsonData = new String(Files.readAllBytes(Path.of(FILE_PATH)));

        long partLastId = partService.findLastId();

        CarSeedDTO[] carSeedDTOS = gson.fromJson(jsonData, CarSeedDTO[].class);

        for (CarSeedDTO carSeedDTO : carSeedDTOS) {
            Car carEntity = mapCarSeedDTOtoCarEntity(carSeedDTO, partLastId);

            carRepository.saveAndFlush(carEntity);
        }
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
    public boolean exportToJSONCarsByToyotaMakerOrderedModelAscTravelledDistanceDesc() {
        String EXPORT_RESULT_JSON_FILE_PATH =
            "src/main/resources/static/resultsJSON/toyotaCarsOrderedByModelAscTravelledDistanceDesc.json";

        Set<Car> toyotaCars = carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota");

        try (FileWriter writer = new FileWriter(EXPORT_RESULT_JSON_FILE_PATH)) {

            List<String> jsonObjectsData = new ArrayList<>();

            for (Car toyotaCar : toyotaCars) {
                CarExportDTO carExportDTO = mapCarEntityToCarExportDTO(toyotaCar);
                jsonObjectsData.add(gson.toJson(carExportDTO));
            }

            writer.write(jsonObjectsData.toString());
            System.out.println("Successful operation!");
            return true;

        } catch (IOException e) {
            System.out.println("An Error occurred while Writing the Data!");
            return false;
        }
    }

    @Override
    public boolean exportToJSONCarsAndTheirParts() {
         String EXPORT_RESULT_JSON_FILE_PATH =
            "src/main/resources/static/resultsJSON/carsAndTheirParts.json";

        List<Car> cars = carRepository.findAll();

        try (FileWriter writer = new FileWriter(EXPORT_RESULT_JSON_FILE_PATH)) {

            List<String> jsonObjectsData = new ArrayList<>();

            for (Car car : cars) {
                CarDataExportDTO carDataExportDTO = mapCarEntityToCarDataExportDTO(car);
                jsonObjectsData.add(gson.toJson(carDataExportDTO));
            }

            writer.write(jsonObjectsData.toString());
            System.out.println("Successful operation!");
            return true;

        } catch (IOException e) {
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

    private CarExportDTO mapCarEntityToCarExportDTO(Car car) {
        return CarExportDTO
            .builder()
            .Id(car.getId())
            .Make(car.getMake())
            .Model(car.getModel())
            .TravelledDistance(car.getTravelledDistance())
            .build();
    }

    private CarDataExportDTO mapCarEntityToCarDataExportDTO(Car car) {
        return CarDataExportDTO
                .builder()
                .make(car.getMake())
                .model(car.getModel())
                .TravelledDistance(car.getTravelledDistance())
                .parts(mapPartEntityToPartDataExportDTO(car.getParts()))
                .build();
    }

    private List<PartDataExportDTO> mapPartEntityToPartDataExportDTO(Set<Part> parts) {
        List<PartDataExportDTO> result = new ArrayList<>();
        for (Part part : parts) {
            result.add(PartDataExportDTO
                .builder()
                .Name(part.getName())
                .Price(part.getPrice())
                .build());
        }
        return result;
    }
}
