package com.ItCareerElevatorSixthExercise.repositories.reservation;

import com.ItCareerElevatorSixthExercise.entities.reservation.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.entities.reservation.ReservedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservedProductRepository extends JpaRepository<ReservedProduct, Long> {

    void deleteAllByProcessedOrder(ProcessedOrder order);

    List<ReservedProduct> findAllByProcessedOrder(ProcessedOrder processedOrder);
}
