package bg.softuni.jsonprocessingexercisepart2.services.impls;

import bg.softuni.jsonprocessingexercisepart2.DTOs.exports.SupplierExportDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.seeds.SupplierSeedDTO;
import bg.softuni.jsonprocessingexercisepart2.entities.Supplier;
import bg.softuni.jsonprocessingexercisepart2.repositories.SupplierRepository;
import bg.softuni.jsonprocessingexercisepart2.services.interfaces.SupplierService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private static final String FILE_PATH = "src/main/resources/static/JSON/suppliers.json";

    private final Gson gson;

    @Override
    public void seedSuppliers() throws IOException {
        if (supplierRepository.count() > 0) return;

        String jsonData = new String(Files.readAllBytes(Path.of(FILE_PATH)));

        SupplierSeedDTO[] supplierSeedDTOS = gson.fromJson(jsonData, SupplierSeedDTO[].class);

        for (SupplierSeedDTO supplierSeedDTO : supplierSeedDTOS) {
            Supplier supplierEntity = Supplier.builder()
                .name(supplierSeedDTO.getName())
                .isImporter(supplierSeedDTO.isImporter())
                .build();
            supplierRepository.saveAndFlush(supplierEntity);
        }
    }

    @Override
    public long findLastId() {
        return supplierRepository.count();
    }

    @Override
    public Supplier findById(long id) {
        return supplierRepository.findById(id).get();
    }

    @Override
    public boolean exportToJSONSuppliersWithPartsNotFromAbroad() {
        String EXPORT_RESULT_JSON_FILE_PATH =
            "src/main/resources/static/resultsJSON/suppliersNotFromAbroad.json";

        Set<Supplier> suppliers = supplierRepository.findAllByImporterIsFalse();

        try (FileWriter writer = new FileWriter(EXPORT_RESULT_JSON_FILE_PATH)) {
            List<String> jsonObjectsData = new ArrayList<>();

            for (Supplier supplier : suppliers) {
                SupplierExportDTO supplierExportDTO = mapSupplierEntityToSupplierExportDTO(supplier);
                jsonObjectsData.add(gson.toJson(supplierExportDTO));
            }

            writer.write(jsonObjectsData.toString());
            System.out.println("Successful operation!");
            return true;

        } catch (IOException e) {
            System.out.println("An Error occurred while Writing the Data!");
            return false;
        }
    }

    private SupplierExportDTO mapSupplierEntityToSupplierExportDTO(Supplier supplier) {
        return SupplierExportDTO
            .builder()
            .Id(supplier.getId())
            .Name(supplier.getName())
            .partsCount(supplier.getParts().size())
            .build();
    }
}
