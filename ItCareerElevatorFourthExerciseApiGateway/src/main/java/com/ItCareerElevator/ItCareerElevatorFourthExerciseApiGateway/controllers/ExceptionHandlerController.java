package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.controllers;

import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.common.ErrorResponseDTO;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.exceptions.InvalidCredentialsException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.exceptions.ManagementMicroserviceException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.exceptions.NoSuchProductException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.exceptions.OrdersMicroserviceException;
import com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.exceptions.UserAlreadyExistsException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        log.warn("Handling UserAlreadyExistsException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid username or password.", // ! Don't tell the user explicitly that the username is already taken.
                System.currentTimeMillis()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(error);
    }

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

    @ExceptionHandler(OrdersMicroserviceException.class)
    public ResponseEntity<ErrorResponseDTO> handleOrdersMicroserviceException(OrdersMicroserviceException ex) {
        log.warn("Handling OrdersMicroserviceException.");
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

    @ExceptionHandler(ManagementMicroserviceException.class)
    public ResponseEntity<ErrorResponseDTO> handleManagementMicroserviceException(ManagementMicroserviceException ex) {
        log.warn("Handling ManagementMicroserviceException.");
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

    @ExceptionHandler(InvalidCredentialsException.class) // * Thrown in authenticate -> catch block
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
    // ? Probably already handled in JwtRequestFilter (if it's thrown only there)
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

    @ExceptionHandler(ExpiredJwtException.class) // * Expired JWT token
    public ResponseEntity<ErrorResponseDTO> handleExpiredJwtException(ExpiredJwtException ex) {
        log.warn("Handling ExpiredJwtException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.UNAUTHORIZED.value(),
                "Invalid or missing authentication credentials.",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    // * Thrown when an argument annotated with @Valid fails validation checks
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
    // * Thrown when validation constraints on method parameters (e.g., path variables, query parameters) or method return values fail. This requires the containing class (Controller/Service) to be annotated with @Validated.
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
    // * Thrown when the incoming HTTP request body cannot be converted to the required object type (malformed JSON, incorrect data type for a field)
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
