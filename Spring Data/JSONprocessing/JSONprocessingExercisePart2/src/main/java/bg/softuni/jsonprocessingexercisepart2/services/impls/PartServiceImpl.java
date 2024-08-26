package bg.softuni.jsonprocessingexercisepart2.services.impls;

import bg.softuni.jsonprocessingexercisepart2.DTOs.seeds.PartSeedDTO;
import bg.softuni.jsonprocessingexercisepart2.entities.Part;
import bg.softuni.jsonprocessingexercisepart2.entities.Supplier;
import bg.softuni.jsonprocessingexercisepart2.repositories.PartRepository;
import bg.softuni.jsonprocessingexercisepart2.services.interfaces.PartService;
import bg.softuni.jsonprocessingexercisepart2.services.interfaces.SupplierService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;
    private final SupplierService supplierService;
    private static final String FILE_PATH = "src/main/resources/static/JSON/parts.json";

    private final Gson gson;

    @Override
    public void seedParts() throws IOException {
        if (partRepository.count() > 0) return;

        String jsonData = new String(Files.readAllBytes(Path.of(FILE_PATH)));
        long supplierLastId = supplierService.findLastId();

        PartSeedDTO[] partSeedDTOS = gson.fromJson(jsonData, PartSeedDTO[].class);

        for (PartSeedDTO partSeedDTO : partSeedDTOS) {
            Part partEntity = mapPartSeedDTOtoPartEntity(partSeedDTO, supplierLastId);

            partRepository.saveAndFlush(partEntity);
        }
    }

    @Override
    public long findLastId() {
        return partRepository.count();
    }

    @Override
    public Part findById(long id) {
        return partRepository.findById(id).get();
    }

    private Part mapPartSeedDTOtoPartEntity(PartSeedDTO partSeedDTO, long lastSupplierId) {
        Supplier supplier = supplierService
            .findById(new Random().nextLong(1, lastSupplierId + 1));

        return new Part(
            partSeedDTO.getName(),
            partSeedDTO.getPrice(),
            partSeedDTO.getQuantity(),
            Set.of(),
            supplier
        );
    }
}
