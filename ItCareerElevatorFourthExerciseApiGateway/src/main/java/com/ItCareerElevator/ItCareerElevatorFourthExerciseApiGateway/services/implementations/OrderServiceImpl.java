package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.services.implementations;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.common.ErrorResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.orders.request.MsvcOrderRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.orders.request.OrderRequestDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.orders.response.OrderResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.entities.User;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.exceptions.OrdersMicroserviceException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.services.interfaces.OrderService;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final WebClient ordersWebClient;
    private final UserService userService;

    @Override
    public OrderResponseDTO create(OrderRequestDTO requestDTO) {
        log.info("Making a request to the orders microservice.");

        return ordersWebClient
                .post()
                .uri("/api/orders")
                .bodyValue(constructMsvcOrderRequestDTO(requestDTO))
                .retrieve()
                .onStatus(HttpStatusCode::isError, // TODO: Look for a better approach (test all possible custom errors)
                        resp -> resp
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(OrdersMicroserviceException::new)
                                .flatMap(Mono::error)
                )
                .bodyToMono(OrderResponseDTO.class)
                .block();
    }

    private MsvcOrderRequestDTO constructMsvcOrderRequestDTO(OrderRequestDTO requestDTO) {
        User currentlyLoggedUser = userService.getCurrentlyLoggedUser();

        return new MsvcOrderRequestDTO(
                currentlyLoggedUser.getId(),
                requestDTO.getProducts()
        );
    }
}
