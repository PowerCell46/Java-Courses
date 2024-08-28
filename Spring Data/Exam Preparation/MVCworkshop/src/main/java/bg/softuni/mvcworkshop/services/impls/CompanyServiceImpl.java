package bg.softuni.mvcworkshop.services.impls;

import bg.softuni.mvcworkshop.DTOs.CompanyRootSeedDTO;
import bg.softuni.mvcworkshop.DTOs.CompanySeedDTO;
import bg.softuni.mvcworkshop.entities.Company;
import bg.softuni.mvcworkshop.repositories.CompanyRepository;
import bg.softuni.mvcworkshop.services.interfaces.CompanyService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private static final String FILE_PATH = "src/main/resources/files/xmls/companies.xml";

    @Override
    public long findCount() {
        return companyRepository.count();
    }

    @Override
    public String getXMLdata() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public boolean seedXMLdata(String companiesData) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CompanyRootSeedDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        CompanyRootSeedDTO rootSeedDTO = (CompanyRootSeedDTO) unmarshaller.unmarshal(new File(FILE_PATH));

        for (CompanySeedDTO companySeedDTO : rootSeedDTO.getCompanies()) {
            Company companyEntity = new Company(companySeedDTO.getName());
            companyRepository.saveAndFlush(companyEntity);
        }

        return true;
    }

    @Override
    public Optional<Company> findByName(String name) {
        return companyRepository.findByName(name);
    }
}
