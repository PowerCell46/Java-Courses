package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.controllers;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.orders.request.OrderRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.orders.response.OrderResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.services.interfaces.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO requestDTO) {
        log.info("---> POST request on api/orders with {} unique products.", requestDTO.getProducts().size());

        OrderResponseDTO responseDTO = orderService.create(requestDTO);

        URI location = URI.create(String.format("/api/orders/%s", responseDTO.getOrderId()));
        return ResponseEntity.created(location).body(responseDTO);
    }

    // Get single order (Maybe it's better to call the invoice microservice [it holds the invoices])

    // Get all user's orders (Maybe it's better to call the invoice microservice [it holds the invoices])
}
