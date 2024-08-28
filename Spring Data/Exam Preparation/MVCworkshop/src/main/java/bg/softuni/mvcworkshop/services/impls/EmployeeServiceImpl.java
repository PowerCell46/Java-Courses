package bg.softuni.mvcworkshop.services.impls;

import bg.softuni.mvcworkshop.DTOs.EmployeeRootSeedDTO;
import bg.softuni.mvcworkshop.DTOs.EmployeeSeedDTO;
import bg.softuni.mvcworkshop.entities.Employee;
import bg.softuni.mvcworkshop.repositories.EmployeeRepository;
import bg.softuni.mvcworkshop.services.interfaces.EmployeeService;
import bg.softuni.mvcworkshop.services.interfaces.ProjectService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ProjectService projectService;
    private static final String FILE_PATH = "src/main/resources/files/xmls/employees.xml";

    @Override
    public long findCount() {
        return employeeRepository.count();
    }

    @Override
    public String getXMLdata() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public boolean seedXMLdata(String employees) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(EmployeeRootSeedDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        EmployeeRootSeedDTO employeeRootSeedDTO = (EmployeeRootSeedDTO) unmarshaller.unmarshal(new File(FILE_PATH));

        for (EmployeeSeedDTO employeeDTO : employeeRootSeedDTO.getEmployees()) {
            employeeRepository.saveAndFlush(new Employee(
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                employeeDTO.getAge(),
                projectService
                    .findByName(employeeDTO.getProject().getName())
                    .orElse(null)
            ));
        }
        return true;
    }
}
