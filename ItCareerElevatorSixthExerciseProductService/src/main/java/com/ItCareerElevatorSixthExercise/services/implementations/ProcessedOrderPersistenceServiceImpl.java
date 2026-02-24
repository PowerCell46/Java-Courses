package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrderStatus;
import com.ItCareerElevatorSixthExercise.repositories.ProcessedOrderRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProcessedOrderPersistenceService;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProcessedOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessedOrderPersistenceServiceImpl implements ProcessedOrderPersistenceService {

    private final ProcessedOrderRepository processedOrderRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ProcessedOrder saveNotInStock(Long orderId) {
        log.info("Persisting NOT_IN_STOCK processed order with id {} in an independent transaction.", orderId);
        return processedOrderRepository.save(new ProcessedOrder(orderId, ProcessedOrderStatus.NOT_IN_STOCK));
    }
}
