package com.ItCareerElevatorSixthExercise.controllers;

import com.ItCareerElevatorSixthExercise.DTOs.common.ErrorResponseDTO;
import com.ItCareerElevatorSixthExercise.exceptions.NoSuchOrderFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

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
