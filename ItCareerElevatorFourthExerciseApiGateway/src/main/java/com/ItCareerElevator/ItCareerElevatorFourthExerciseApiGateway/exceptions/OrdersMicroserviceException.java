package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.exceptions;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.common.ErrorResponseDTO;
import lombok.Getter;

@Getter
public class OrdersMicroserviceException extends RuntimeException {

    private final Integer status;
    private final Long timestamp;

    public OrdersMicroserviceException(ErrorResponseDTO errorResponseDTO) {
        super(errorResponseDTO.getMessage());
        this.status = errorResponseDTO.getStatus();
        this.timestamp = errorResponseDTO.getTimestamp();
    }
}
