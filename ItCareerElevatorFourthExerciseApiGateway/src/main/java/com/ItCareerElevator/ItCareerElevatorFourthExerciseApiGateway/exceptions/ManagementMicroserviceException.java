package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.exceptions;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.common.ErrorResponseDTO;
import lombok.Getter;

@Getter
public class ManagementMicroserviceException extends RuntimeException {

    private final Integer status;
    private final Long timestamp;

    public ManagementMicroserviceException(ErrorResponseDTO errorResponseDTO) {
        super(errorResponseDTO.getMessage());
        this.status = errorResponseDTO.getStatus();
        this.timestamp = errorResponseDTO.getTimestamp();
    }
}
