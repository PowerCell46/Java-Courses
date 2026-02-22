package com.ItCareerElevatorSixthExercise.exceptions.msvc;

import com.ItCareerElevatorSixthExercise.DTOs.common.ErrorResponseDTO;
import lombok.Getter;

@Getter
public class MicroserviceException extends RuntimeException {

    private final Integer status;

    private final Long timestamp;

    public MicroserviceException(ErrorResponseDTO errorResponseDTO) {
        super(errorResponseDTO.getMessage());
        this.status = errorResponseDTO.getStatus();
        this.timestamp = errorResponseDTO.getTimestamp();
    }
}
