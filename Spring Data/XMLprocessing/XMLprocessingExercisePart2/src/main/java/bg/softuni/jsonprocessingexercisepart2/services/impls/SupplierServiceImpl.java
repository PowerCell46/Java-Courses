package bg.softuni.jsonprocessingexercisepart2.services.impls;

import bg.softuni.jsonprocessingexercisepart2.DTOs.queries.SupplierQueryDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.queries.SupplierRootQueryDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.seedings.SupplierRootSeedDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.seeds.SupplierSeedDTO;
import bg.softuni.jsonprocessingexercisepart2.entities.Supplier;
import bg.softuni.jsonprocessingexercisepart2.repositories.SupplierRepository;
import bg.softuni.jsonprocessingexercisepart2.services.interfaces.SupplierService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private static final String FILE_PATH = "src/main/resources/static/XML/suppliers.xml";

    private final Gson gson;

    @Override
    public void seedSuppliers() throws JAXBException {
        if (supplierRepository.count() > 0) return;

        JAXBContext jaxbContext = JAXBContext.newInstance(SupplierRootSeedDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        SupplierRootSeedDTO supplierRootSeedDTO = (SupplierRootSeedDTO) unmarshaller.unmarshal(new File(FILE_PATH));

        supplierRootSeedDTO.getSuppliers().stream().forEach(supplierSeedDTO ->
            supplierRepository.saveAndFlush(Supplier
                .builder()
                .name(supplierSeedDTO.getName())
                .isImporter(supplierSeedDTO.isImporter())
                .build())
        );
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
    public boolean exportToXMLSuppliersWithPartsNotFromAbroad() {
        String EXPORT_RESULT_XML_FILE_PATH =
            "src/main/resources/static/resultsXML/suppliersNotFromAbroad.xml";

        Set<Supplier> suppliers = supplierRepository.findAllByImporterIsFalse();

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(SupplierRootQueryDTO.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(
                new SupplierRootQueryDTO(
                    suppliers
                        .stream()
                        .map(supplier ->
                            mapSupplierEntityToSupplierExportDTO(supplier)).toList()),
                new File(EXPORT_RESULT_XML_FILE_PATH)
            );

            System.out.println("Successful operation!");
            return true;

        } catch (JAXBException e) {
            System.out.println("An Error occurred while Writing the Data!");
            return false;
        }
    }

    private SupplierQueryDTO mapSupplierEntityToSupplierExportDTO(Supplier supplier) {
        return SupplierQueryDTO
            .builder()
            .id(supplier.getId())
            .name(supplier.getName())
            .partsCount(supplier.getParts().size())
            .build();
    }
}
