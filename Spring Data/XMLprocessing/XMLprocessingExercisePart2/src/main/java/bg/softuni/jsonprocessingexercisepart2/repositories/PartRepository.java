package bg.softuni.jsonprocessingexercisepart2.repositories;

import bg.softuni.jsonprocessingexercisepart2.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
}
