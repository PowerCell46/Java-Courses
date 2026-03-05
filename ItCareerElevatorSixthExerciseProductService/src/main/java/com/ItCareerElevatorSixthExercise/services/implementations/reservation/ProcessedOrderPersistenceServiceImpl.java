package com.ItCareerElevatorSixthExercise.services.implementations.reservation;

import com.ItCareerElevatorSixthExercise.entities.reservation.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.repositories.reservation.ProcessedOrderRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.reservation.ProcessedOrderPersistenceService;
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

    @Override
    @Transactional
    public void deleteById(Long id) {
        processedOrderRepository.deleteById(id);
    }
}
