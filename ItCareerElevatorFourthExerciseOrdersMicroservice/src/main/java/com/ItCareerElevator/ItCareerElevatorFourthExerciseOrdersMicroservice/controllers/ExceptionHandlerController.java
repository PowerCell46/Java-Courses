package com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.controllers;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.DTOs.response.ErrorResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.exceptions.NoSuchProductException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseOrdersMicroservice.exceptions.NoSuchUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(NoSuchProductException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoSuchProductException(NoSuchProductException ex) {
        log.warn("Handling NoSuchProductException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(error);
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoSuchUserException(NoSuchUserException ex) {
        log.warn("Handling NoSuchUserException.");

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
