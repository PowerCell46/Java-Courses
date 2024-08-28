package bg.softuni.jsonprocessingexercisepart2.repositories;

import bg.softuni.jsonprocessingexercisepart2.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Set<Car> findAllByMakeOrderByModelAscTravelledDistanceDesc(String model);
}
