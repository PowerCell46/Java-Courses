package com.ItCareerElevatorSixthExercise.controllers;

import com.ItCareerElevatorSixthExercise.DTOs.common.ErrorResponseDTO;
import com.ItCareerElevatorSixthExercise.exceptions.auth.InvalidCredentialsException;
import com.ItCareerElevatorSixthExercise.exceptions.auth.NoSuchUserException;
import com.ItCareerElevatorSixthExercise.exceptions.msvc.DefaultMicroserviceException;
import com.ItCareerElevatorSixthExercise.exceptions.msvc.OrderServiceException;
import com.ItCareerElevatorSixthExercise.exceptions.msvc.ProductServiceException;
import com.ItCareerElevatorSixthExercise.exceptions.msvc.UserServiceException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(DefaultMicroserviceException.class)
    public ResponseEntity<ErrorResponseDTO> handleMicroserviceException(DefaultMicroserviceException ex) {
        log.warn("Handling DefaultMicroserviceException.");
        log.warn("Error status: {}, message: {}", ex.getStatus(), ex.getMessage());

        ErrorResponseDTO error = new ErrorResponseDTO(
                ex.getStatus(),
                ex.getMessage(),
                ex.getTimestamp()
        );

        return ResponseEntity
                .status(ex.getStatus())
                .body(error);
    }

    @ExceptionHandler(OrderServiceException.class)
    public ResponseEntity<ErrorResponseDTO> handleOrderServiceException(OrderServiceException ex) {
        log.warn("Handling OrderServiceException.");
        log.warn("Error status: {}, message: {}", ex.getStatus(), ex.getMessage());

        ErrorResponseDTO error = new ErrorResponseDTO(
                ex.getStatus(),
                ex.getMessage(),
                ex.getTimestamp()
        );

        return ResponseEntity
                .status(ex.getStatus())
                .body(error);
    }

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<ErrorResponseDTO> handleProductServiceException(ProductServiceException ex) {
        log.warn("Handling ProductServiceException.");
        log.warn("Error status: {}, message: {}", ex.getStatus(), ex.getMessage());

        ErrorResponseDTO error = new ErrorResponseDTO(
                ex.getStatus(),
                ex.getMessage(),
                ex.getTimestamp()
        );

        return ResponseEntity
                .status(ex.getStatus())
                .body(error);
    }

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserServiceException(UserServiceException ex) {
        log.warn("Handling UserServiceException.");
        log.warn("Error status: {}, message: {}", ex.getStatus(), ex.getMessage());

        ErrorResponseDTO error = new ErrorResponseDTO(
                ex.getStatus(),
                ex.getMessage(),
                ex.getTimestamp()
        );

        return ResponseEntity
                .status(ex.getStatus())
                .body(error);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        log.warn("Handling MissingServletRequestPartException");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "The file part of the request is missing.",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
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

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoSuchUserException(NoSuchUserException ex) {
        log.warn("Handling NoSuchUserException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(error);
    }

    @ExceptionHandler(SignatureException.class) // ? Probably already handled in JwtRequestFilter (if it's thrown only there)
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

    @ExceptionHandler(MethodArgumentNotValidException.class) // * Thrown when an argument annotated with @Valid fails validation checks
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

    @ExceptionHandler(ConstraintViolationException.class) // * Thrown when validation constraints on method parameters (e.g., path variables, query parameters) or method return values fail. This requires the containing class (Controller/Service) to be annotated with @Validated.
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

    @ExceptionHandler(HttpMessageNotReadableException.class) // * Thrown when the incoming HTTP request body cannot be converted to the required object type (malformed JSON, incorrect data type for a field) (e.g. property type mismatch, missing quotes)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolation(HttpMessageNotReadableException ex) {
        log.warn("Handling HttpMessageNotReadableException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Request body contains values that do not match the expected data types.",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class) // * Thrown when user makes a type of request on an endpoint, that is not supported/defined.
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolation(HttpRequestMethodNotSupportedException ex) {
        log.warn("Handling HttpRequestMethodNotSupportedException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                String.format("Method %s is not supported for this endpoint.", ex.getMethod()),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }
}
