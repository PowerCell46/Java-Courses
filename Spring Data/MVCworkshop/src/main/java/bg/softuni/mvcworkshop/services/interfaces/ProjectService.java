package bg.softuni.mvcworkshop.services.interfaces;

import bg.softuni.mvcworkshop.entities.Project;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.util.Optional;

public interface ProjectService {

    long findCount();

    String getXMLdata() throws IOException;

    boolean seedXMLdata(String projectsData) throws JAXBException;

    Optional<Project> findByName(String name);

    String getFinishedProjects();
}
