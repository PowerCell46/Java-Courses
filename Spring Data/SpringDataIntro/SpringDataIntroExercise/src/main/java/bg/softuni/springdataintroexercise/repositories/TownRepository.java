package bg.softuni.springdataintroexercise.repositories;

import bg.softuni.springdataintroexercise.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownRepository extends JpaRepository<Town, Long> {
}
