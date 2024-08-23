package bg.softuni.springdataintroexercise.repositories;

import bg.softuni.springdataintroexercise.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
