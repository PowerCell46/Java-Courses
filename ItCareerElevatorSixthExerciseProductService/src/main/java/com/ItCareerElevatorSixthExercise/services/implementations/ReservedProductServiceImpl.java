package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.kafka.reserveItems.OrderItemDTO;
import com.ItCareerElevatorSixthExercise.entities.ProcessedOrder;
import com.ItCareerElevatorSixthExercise.entities.ReservedProduct;
import com.ItCareerElevatorSixthExercise.repositories.ReservedProductRepository;
import com.ItCareerElevatorSixthExercise.services.interfaces.ProductService;
import com.ItCareerElevatorSixthExercise.services.interfaces.ReservedProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservedProductServiceImpl implements ReservedProductService {

    private final ProductService productService;
    private final ReservedProductRepository reservedProductRepository;

    @Override
    public void initializeOrderItems(List<OrderItemDTO> orderItems, ProcessedOrder processedOrder) {
        orderItems
                .forEach(orderItemDTO -> save(instantiateFromOrderItem(orderItemDTO, processedOrder)));
    }

    @Override
    public List<ReservedProduct> getAllByProcessedOrder(ProcessedOrder processedOrder) {
        return reservedProductRepository.findAllByProcessedOrder(processedOrder);
    }

    public ReservedProduct save(ReservedProduct reservedProduct) {
        log.info("Persisting reservedProduct to the database.");

        return reservedProductRepository.save(reservedProduct);
    }

    private ReservedProduct instantiateFromOrderItem(OrderItemDTO orderItemDTO, ProcessedOrder processedOrder) {
        return new ReservedProduct(
                processedOrder,
                productService.getById(orderItemDTO.getProductId()),
                orderItemDTO.getQuantity()
        );
    }
}
