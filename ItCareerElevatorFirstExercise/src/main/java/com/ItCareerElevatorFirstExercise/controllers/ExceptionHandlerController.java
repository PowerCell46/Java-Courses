package com.ItCareerElevatorFirstExercise.controllers;

import com.ItCareerElevatorFirstExercise.DTOs.ErrorResponseDTO;
import com.ItCareerElevatorFirstExercise.exceptions.InvalidAliasException;
import com.ItCareerElevatorFirstExercise.exceptions.InvalidSnowflakeIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(InvalidAliasException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(InvalidAliasException ex) {
        log.warn("Handling invalid alias exception.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidSnowflakeIdException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(InvalidSnowflakeIdException ex) {
        log.warn("Handling invalid snowflakeId exception.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
