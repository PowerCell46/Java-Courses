package bg.softuni.jsonprocessingexercisepart2.services.impls;

import bg.softuni.jsonprocessingexercisepart2.DTOs.seedings.PartRootSeedDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.seedings.PartSeedDTO;
import bg.softuni.jsonprocessingexercisepart2.entities.Part;
import bg.softuni.jsonprocessingexercisepart2.entities.Supplier;
import bg.softuni.jsonprocessingexercisepart2.repositories.PartRepository;
import bg.softuni.jsonprocessingexercisepart2.services.interfaces.PartService;
import bg.softuni.jsonprocessingexercisepart2.services.interfaces.SupplierService;
import com.google.gson.Gson;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
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
    private static final String FILE_PATH = "src/main/resources/static/XML/parts.xml";

    private final Gson gson;

    @Override
    public void seedParts() throws JAXBException {
        if (partRepository.count() > 0) return;

        long supplierLastId = supplierService.findLastId();

        JAXBContext jaxbContext = JAXBContext.newInstance(PartRootSeedDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        PartRootSeedDTO partRootSeedDTO = (PartRootSeedDTO) unmarshaller.unmarshal(new File(FILE_PATH));

        partRootSeedDTO
            .getParts()
            .stream()
            .forEach(partSeedDTO ->
                partRepository.saveAndFlush(
                    mapPartSeedDTOtoPartEntity(partSeedDTO, supplierLastId)
                )
            );
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
