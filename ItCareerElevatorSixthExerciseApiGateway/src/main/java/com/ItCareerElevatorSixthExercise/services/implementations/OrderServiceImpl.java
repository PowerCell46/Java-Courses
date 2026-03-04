package com.ItCareerElevatorSixthExercise.services.implementations;

import com.ItCareerElevatorSixthExercise.DTOs.common.ErrorResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.order.request.msvc.MsvcOrderRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.order.request.OrderRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.order.response.OrderResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;
import com.ItCareerElevatorSixthExercise.exceptions.msvc.OrderServiceException;
import com.ItCareerElevatorSixthExercise.services.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.ItCareerElevatorSixthExercise.utils.common.RetryPolicy.buildRetrySpec;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final WebClient orderServiceWebClient;

    @Override
    public OrderResponseDTO create(OrderRequestDTO requestDTO, User user) {
        log.info("---| Making a request to the orderMicroservice.");

        var requestBody = new MsvcOrderRequestDTO(user.getId(), user.getEmail(), requestDTO.getItems());

        return orderServiceWebClient
                .post()
                .uri("/api/orders")
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        res -> res
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(OrderServiceException::new)
                                .flatMap(Mono::error))
                .bodyToMono(OrderResponseDTO.class)
                .retryWhen(buildRetrySpec())
                .block();
    }

    @Override
    public OrderResponseDTO getById(String id, User user) {
        return orderServiceWebClient
                .get()
                .uri(String.format("/api/orders/status/%s?userId=%s", id, user.getId()))
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        res -> res
                                .bodyToMono(ErrorResponseDTO.class)
                                .map(OrderServiceException::new)
                                .flatMap(Mono::error))
                .bodyToMono(OrderResponseDTO.class)
                .retryWhen(buildRetrySpec())
                .block();
    }
}
