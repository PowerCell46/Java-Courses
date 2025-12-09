package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.controllers;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.common.ErrorResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.exceptions.FeedMicroserviceException;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.exceptions.InvalidCredentialsException;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.exceptions.PersistenceMicroserviceException;
import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.exceptions.UserAlreadyExistsException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.security.SignatureException;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlerController {

    @ExceptionHandler(PersistenceMicroserviceException.class)
    public ResponseEntity<ErrorResponseDTO> handlePersistenceMicroserviceException(PersistenceMicroserviceException ex) {
        log.warn("Handling PersistenceMicroserviceException.");
        log.warn("Error status: {}, message: {}.", ex.getStatus(), ex.getMessage());

        ErrorResponseDTO error = new ErrorResponseDTO(
                ex.getStatus(),
                ex.getMessage(),
                ex.getTimestamp()
        );

        return ResponseEntity
                .status(ex.getStatus())
                .body(error);
    }

    @ExceptionHandler(FeedMicroserviceException.class)
    public ResponseEntity<ErrorResponseDTO> handleFeedMicroserviceException(FeedMicroserviceException ex) {
        log.warn("Handling FeedMicroserviceException.");
        log.warn("Error status: {}, message: {}.", ex.getStatus(), ex.getMessage());

        ErrorResponseDTO error = new ErrorResponseDTO(
                ex.getStatus(),
                ex.getMessage(),
                ex.getTimestamp()
        );

        return ResponseEntity
                .status(ex.getStatus())
                .body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        log.warn("Handling UserAlreadyExistsException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid username or password.",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        log.warn("Handling InvalidCredentialsException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(SignatureException ex) {
        log.warn("Handling SignatureException.");

        ErrorResponseDTO error =
                new ErrorResponseDTO(
                        HttpStatus.UNAUTHORIZED.value(),
                        "Invalid token signature.",
                        System.currentTimeMillis()
                );

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponseDTO> handleExpiredJwtException(ExpiredJwtException ex) {
        log.warn("Handling ExpiredJwtException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.UNAUTHORIZED.value(),
                "Token has expired.",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(MethodArgumentNotValidException ex) {
        log.warn("Handling MethodArgumentNotValidException.");

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Request data validation failed.");

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
                .orElse("Request data validation failed.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                errorMessage,
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_CONTENT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolation(HttpMessageNotReadableException ex) {
        log.warn("Handling HttpMessageNotReadableException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Request body contains values that do not match the expected data types.",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
