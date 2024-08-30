package bg.softuni.xmlprocessingexercisepart2.repositories;

import bg.softuni.xmlprocessingexercisepart2.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
}
