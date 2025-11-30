package com.example.ItCareerElevatorSecondExerciseProducer.controllers;

import com.example.ItCareerElevatorSecondExerciseProducer.entities.ErrorResponseDTO;
import com.example.ItCareerElevatorSecondExerciseProducer.exceptions.InvalidRelationshipException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(InvalidRelationshipException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(InvalidRelationshipException ex) {
        log.warn("Handling invalid relationship exception.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
