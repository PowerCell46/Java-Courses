package bg.softuni.xmlprocessingexercisepart2.repositories;

import bg.softuni.xmlprocessingexercisepart2.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
}
