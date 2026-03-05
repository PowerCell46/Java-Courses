package com.ItCareerElevatorSixthExercise.services.interfaces.reservation;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.reserveItems.ReserveOrderItemDTO;
import com.ItCareerElevatorSixthExercise.entities.reservation.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.entities.reservation.ReservedProduct;

import java.util.List;

public interface ReservedProductService {

    ReservedProduct save(ReservedProduct reservedProduct);

    void initializeOrderItems(List<ReserveOrderItemDTO> orderItems, ProcessedOrder processedOrder);

    List<ReservedProduct> getAllByProcessedOrder(ProcessedOrder processedOrder);
}
