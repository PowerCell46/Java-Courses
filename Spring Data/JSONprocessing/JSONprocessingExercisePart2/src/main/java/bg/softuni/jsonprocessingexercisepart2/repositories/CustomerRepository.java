package bg.softuni.jsonprocessingexercisepart2.repositories;

import bg.softuni.jsonprocessingexercisepart2.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c ORDER BY c.birthDate, c.isYoungDriver")
    Set<Customer> getAllOrderByBirthDateAndIsYoungDriver();

    Set<Customer> findAllBySalesIsNotEmpty();
}
