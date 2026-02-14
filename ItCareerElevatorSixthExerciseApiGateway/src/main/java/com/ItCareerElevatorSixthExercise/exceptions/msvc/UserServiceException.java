package com.ItCareerElevatorSixthExercise.exceptions.msvc;

import com.ItCareerElevatorSixthExercise.DTOs.common.ErrorResponseDTO;
import lombok.Getter;

@Getter
public class UserServiceException extends RuntimeException {

    private final Integer status;

    private final Long timestamp;

    public UserServiceException(ErrorResponseDTO errorResponseDTO) {
        super(errorResponseDTO.getMessage());
        this.status = errorResponseDTO.getStatus();
        this.timestamp = errorResponseDTO.getTimestamp();
    }
}
