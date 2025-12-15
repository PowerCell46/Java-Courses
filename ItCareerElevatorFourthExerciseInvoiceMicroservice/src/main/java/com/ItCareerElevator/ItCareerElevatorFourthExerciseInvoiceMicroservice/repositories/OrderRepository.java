package com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.repositories;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseInvoiceMicroservice.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    Optional<Order> findByIdAndIsDeletedIsFalse(String id);
}
