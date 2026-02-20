package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.itemsReserved.ReservedOrderDTO;
import com.ItCareerElevatorSixthExercise.entities.OrderStatus;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.repositories.ProcessedOrderRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProcessedOrderService;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessedOrderServiceImpl implements ProcessedOrderService {

    private final UserService userService;
    private final ProcessedOrderRepository processedOrderRepository;

    @Override
    public Optional<ProcessedOrder> findByOrderId(Long orderId) {
        return processedOrderRepository
                .findById(orderId);
    }

    @Override
    public void process(ReservedOrderDTO orderDTO) {
        ProcessedOrder processedOrder;

        if (!userService.isUserWalletAddressPresent(orderDTO.getUserId())) {
            processedOrder = new ProcessedOrder(
                    orderDTO.getOrderId(),
                    OrderStatus.MISSING_WALLET_ADDRESS
            );
            // TODO: Try pushing it in kafka error topic

        } else {
            processedOrder = new ProcessedOrder(
                    orderDTO.getOrderId(),
                    OrderStatus.PROCESSING
            );
        }

        save(processedOrder);
    }

    @Override
    public ProcessedOrder save(ProcessedOrder processedOrder) {
        log.info("Persisting processedOrder with id {}.", processedOrder.getOrderId());
        return processedOrderRepository.save(processedOrder);
    }
}
