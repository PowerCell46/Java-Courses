package bg.softuni.mvcworkshop.services.impls;

import bg.softuni.mvcworkshop.DTOs.ProjectRootSeedDTO;
import bg.softuni.mvcworkshop.DTOs.ProjectSeedDTO;
import bg.softuni.mvcworkshop.entities.Project;
import bg.softuni.mvcworkshop.repositories.ProjectRepository;
import bg.softuni.mvcworkshop.services.interfaces.CompanyService;
import bg.softuni.mvcworkshop.services.interfaces.ProjectService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final CompanyService companyService;
    private static final String FILE_PATH = "src/main/resources/files/xmls/projects.xml";

    @Override
    public long findCount() {
        return projectRepository.count();
    }

    @Override
    public String getXMLdata() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public boolean seedXMLdata(String projectsData) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ProjectRootSeedDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        ProjectRootSeedDTO rootSeedDTO = (ProjectRootSeedDTO) unmarshaller.unmarshal(new File(FILE_PATH));

        for (ProjectSeedDTO projectDTO : rootSeedDTO.getProjects()) {
            LocalDate startDate = LocalDate.of(
                Integer.parseInt(projectDTO.getStartDate().split("-")[0]),
                Integer.parseInt(projectDTO.getStartDate().split("-")[1]),
                Integer.parseInt(projectDTO.getStartDate().split("-")[2])
            );
            projectRepository.saveAndFlush(
                new Project(
                    projectDTO.getName(),
                    projectDTO.getDescription(),
                    projectDTO.isFinished(),
                    projectDTO.getPayment(),
                    startDate,
                    companyService
                        .findByName(projectDTO.getCompany().getName())
                        .orElse(null)
                ));
        }
        return true;
    }

    @Override
    public Optional<Project> findByName(String name) {
        return projectRepository.findByName(name);
    }

    @Override
    public String getFinishedProjects() {
        List<Project> projects = projectRepository.findAllByIsFinishedIsTrue();
        StringBuilder result = new StringBuilder();
        for (Project project : projects) {
            result
                .append("Project Name: ").append(project.getName())
                .append("\n     Description: ").append(project.getDescription())
                .append("\n     ").append(project.getPayment())
                .append("\n");
        }

        return result
            .toString()
            .trim();
    }
}
