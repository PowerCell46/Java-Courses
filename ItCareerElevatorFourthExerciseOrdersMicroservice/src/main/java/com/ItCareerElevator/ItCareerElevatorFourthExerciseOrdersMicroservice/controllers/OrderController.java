package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.controllers;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.request.OrderRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.response.OrderResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO requestDTO) {
        OrderResponseDTO responseDTO = orderService.create(requestDTO);

        URI location = URI.create(String.format("/api/orders/%s", responseDTO.getOrderId()));
        return ResponseEntity.created(location).body(responseDTO);
    }

    // Get single order

    // Get all user orders
}
