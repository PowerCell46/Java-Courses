package com.ItCareerElevatorSixthExercise.exceptions.msvc;

import com.ItCareerElevatorSixthExercise.DTOs.common.ErrorResponseDTO;
import lombok.Getter;

@Getter
public class ProductServiceException extends RuntimeException {

    private final Integer status;

    private final Long timestamp;

    public ProductServiceException(ErrorResponseDTO errorResponseDTO) {
        super(errorResponseDTO.getMessage());
        this.status = errorResponseDTO.getStatus();
        this.timestamp = errorResponseDTO.getTimestamp();
    }
}
