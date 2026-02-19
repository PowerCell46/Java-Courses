package com.ItCareerElevatorSixthExercise.controllers;

import com.ItCareerElevatorSixthExercise.DTOs.order.request.OrderRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.order.response.OrderResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;
import com.ItCareerElevatorSixthExercise.services.interfaces.OrderService;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserService;
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

    private final UserService userService;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO requestDTO) {
        User loggedUser = userService.getCurrentlyLoggedUser();
        log.info("---> POST request on api/orders for user {}.", loggedUser.getUsername());

        var responseDTO = orderService.create(requestDTO, loggedUser);

        URI location = URI.create("/api/orders/status/" + responseDTO.getId());
        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderStatus(@PathVariable String id) {
        log.info("---> GET request on api/orders/status/{}.", id);

        var responseDTO = orderService.getById(id);

        return ResponseEntity.ok(responseDTO);
    }
}
