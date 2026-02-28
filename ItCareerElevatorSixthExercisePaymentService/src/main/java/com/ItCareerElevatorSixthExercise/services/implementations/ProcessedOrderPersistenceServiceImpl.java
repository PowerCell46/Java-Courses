package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.repositories.ProcessedOrderRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProcessedOrderPersistenceService;
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
    public ProcessedOrder save(ProcessedOrder processedOrder) {
        return processedOrderRepository.save(processedOrder);
    }
}
