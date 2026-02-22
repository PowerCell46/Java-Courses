package com.ItCareerElevatorSixthExercise.exceptions;

import com.ItCareerElevatorSixthExercise.DTOs.common.ErrorResponseDTO;
import lombok.Getter;

@Getter
public class PaymentServiceException extends RuntimeException {

    private final Integer status;

    private final Long timestamp;

    public PaymentServiceException(ErrorResponseDTO errorResponseDTO) {
        super(errorResponseDTO.getMessage());
        this.status = errorResponseDTO.getStatus();
        this.timestamp = errorResponseDTO.getTimestamp();
    }
}
