package bg.softuni.automappingobjectsexercise.repositories;

import bg.softuni.automappingobjectsexercise.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
