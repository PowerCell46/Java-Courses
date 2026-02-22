package com.ItCareerElevatorSixthExercise.controllers;

import com.ItCareerElevatorSixthExercise.DTOs.common.ErrorResponseDTO;
import com.ItCareerElevatorSixthExercise.exceptions.InvalidSnowflakeIdException;
import com.ItCareerElevatorSixthExercise.exceptions.NoSuchLoiOrderStatusException;
import com.ItCareerElevatorSixthExercise.exceptions.NoSuchOrderFoundException;
import com.ItCareerElevatorSixthExercise.exceptions.NonUniqueItemsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(InvalidSnowflakeIdException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidSnowflakeIdException(InvalidSnowflakeIdException ex) {
        log.warn("Handling InvalidSnowflakeIdException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(NonUniqueItemsException.class)
    public ResponseEntity<ErrorResponseDTO> handleNonUniqueItemsException(NonUniqueItemsException ex) {
        log.warn("Handling NonUniqueItemsException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(error);
    }

    @ExceptionHandler(NoSuchLoiOrderStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoSuchLoiOrderStatusException(NoSuchLoiOrderStatusException ex) {
        log.warn("Handling NoSuchLoiOrderStatusException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(error);
    }

    @ExceptionHandler(NoSuchOrderFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoSuchOrderFoundException(NoSuchOrderFoundException ex) {
        log.warn("Handling NoSuchOrderFoundException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(error);
    }
}
