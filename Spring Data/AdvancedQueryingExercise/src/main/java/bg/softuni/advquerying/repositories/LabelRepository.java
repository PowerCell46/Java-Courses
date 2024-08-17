package bg.softuni.advquerying.repositories;

import bg.softuni.advquerying.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LabelRepository extends JpaRepository<Label, Long> {
}
