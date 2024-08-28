package bg.softuni.mvcworkshop.repositories;

import bg.softuni.mvcworkshop.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Optional<Project> findByName(String name);
}
