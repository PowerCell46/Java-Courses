package bg.softuni.jsonprocessingexercisepart2.repositories;

import bg.softuni.jsonprocessingexercisepart2.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
}
