package bg.softuni.xmlprocessingexercisepart2.repositories;

import bg.softuni.xmlprocessingexercisepart2.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT s FROM Supplier s WHERE s.isImporter = FALSE")
    Set<Supplier> findAllByImporterIsFalse();
}
