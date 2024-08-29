package bg.softuni.mvcworkshop.repositories;

import bg.softuni.mvcworkshop.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAllByAgeGreaterThan(int age);
}
