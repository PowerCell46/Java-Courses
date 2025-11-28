package com.ItCareerElevatorFirstExercise.controllers;

import com.ItCareerElevatorFirstExercise.DTOs.ErrorResponseDTO;
import com.ItCareerElevatorFirstExercise.exceptions.InvalidAliasException;
import com.ItCareerElevatorFirstExercise.exceptions.InvalidSnowflakeIdException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(MethodArgumentNotValidException ex) {
        log.warn("Handling MethodArgumentNotValidException.");

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Request data validation failed");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                errorMessage,
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_CONTENT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolation(ConstraintViolationException ex) {
        log.warn("Handling ConstraintViolationException.");

        String errorMessage = ex.getConstraintViolations()
                .stream()
                .findFirst()
                .map(ConstraintViolation::getMessage)
                .orElse("Request data validation failed");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                errorMessage,
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_CONTENT);
    }
}
