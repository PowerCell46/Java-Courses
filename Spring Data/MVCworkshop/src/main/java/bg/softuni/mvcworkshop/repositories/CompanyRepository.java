package bg.softuni.mvcworkshop.repositories;

import bg.softuni.mvcworkshop.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Optional<Company> findByName(String name);
}
