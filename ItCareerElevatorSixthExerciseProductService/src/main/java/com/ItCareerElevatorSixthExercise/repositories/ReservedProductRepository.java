package com.ItCareerElevatorSixthExercise.repositories;

import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.entities.ReservedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservedProductRepository extends JpaRepository<ReservedProduct, Long> {

    void deleteAllByProcessedOrder(ProcessedOrder order);

    List<ReservedProduct> findAllByProcessedOrder(ProcessedOrder processedOrder);
}
