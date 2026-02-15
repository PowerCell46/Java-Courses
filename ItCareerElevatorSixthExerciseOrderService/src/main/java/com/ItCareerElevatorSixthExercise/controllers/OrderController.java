package com.ItCareerElevatorSixthExercise.controllers;

import com.ItCareerElevatorSixthExercise.DTOs.request.CreateOrderRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.CreateOrderResponseDTO;
import com.ItCareerElevatorSixthExercise.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<CreateOrderResponseDTO> createOrder(@RequestBody CreateOrderRequestDTO requestDTO) {
        log.info("---> POST request on api/orders for user with id: {}.", requestDTO.getUserId());

        var responseDTO = orderService.create(requestDTO);

        URI location = URI.create("/api/orders/status/" + responseDTO.getId());
        return ResponseEntity.created(location).body(responseDTO);
    }

    // TODO: implement
//    @GetMapping("/status/{id}")
//    public ResponseEntity<> getOrderStatus(@PathVariable String id) {
//
//    }
}
