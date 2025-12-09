package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.exceptions;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.common.ErrorResponseDTO;
import lombok.Getter;

@Getter
public class FeedMicroserviceException extends RuntimeException {

    private final Integer status;
    private final Long timestamp;

    public FeedMicroserviceException(ErrorResponseDTO errorResponseDTO) {
        super(errorResponseDTO.getMessage());
        this.status = errorResponseDTO.getStatus();
        this.timestamp = errorResponseDTO.getTimestamp();
    }
}
