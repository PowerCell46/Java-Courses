package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.reserveItems.OrderDTO;
import com.ItCareerElevatorSixthExercise.DTOs.reserveItems.OrderItemDTO;
import com.ItCareerElevatorSixthExercise.entities.CommonEntity;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.repositories.ProcessedOrderRepository;
import com.ItCareerElevatorSixthExercise.repositories.ProductRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProcessedOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessedOrderServiceImpl implements ProcessedOrderService {

    private final ProductRepository productRepository;
    private final ProcessedOrderRepository processedOrderRepository;

    @Override
    public boolean isOrderProcessed(Long orderId) {
        return processedOrderRepository
                .findById(orderId)
                .isPresent();
    }

    @Override
    @Transactional
    public void processReserveItems(OrderDTO orderDTO) {
        try {
            orderDTO
                    .getOrderItems()
                    .forEach(this::reserveProduct);

            save(orderDTO.getId());

            // TODO: Push to a kafka topic that the products reservation is successful

        } catch (DataIntegrityViolationException ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            log.info("One of the items is not available - returning the other items back in stock.");

            // TODO: Send to topic to make the order status FAILED
        }
    }

    private void reserveProduct(OrderItemDTO orderItemDTO) {
        productRepository
                .decreaseProductInStockQuantity(
                        orderItemDTO.getQuantity(),
                        CommonEntity.convertSnowflakeIdToId(orderItemDTO.getProductId()));
    }

    @Override
    public ProcessedOrder save(Long orderId) {
        log.info("Persisting processed order with id {} to the database.", orderId);

        return processedOrderRepository.save(new ProcessedOrder(orderId));
    }
}
