package com.ItCareerElevatorSixthExercise.controllers;

import com.ItCareerElevatorSixthExercise.DTOs.common.ErrorResponseDTO;
import com.ItCareerElevatorSixthExercise.exceptions.EmailIsAlreadyTakenException;
import com.ItCareerElevatorSixthExercise.exceptions.NoSuchRoleException;
import com.ItCareerElevatorSixthExercise.exceptions.NoSuchUserException;
import com.ItCareerElevatorSixthExercise.exceptions.PaymentServiceException;
import com.ItCareerElevatorSixthExercise.exceptions.UsernameIsAlreadyTakenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(PaymentServiceException.class)
    public ResponseEntity<ErrorResponseDTO> handlePaymentServiceException(PaymentServiceException ex) {
        log.warn("Handling PaymentServiceException.");
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

    @ExceptionHandler(UsernameIsAlreadyTakenException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistsException(UsernameIsAlreadyTakenException ex) {
        log.warn("Handling UsernameIsAlreadyTakenException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid username or password.", // ! Don't tell the user explicitly
                System.currentTimeMillis()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(error);
    }

    @ExceptionHandler(EmailIsAlreadyTakenException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailIsAlreadyTakenException(EmailIsAlreadyTakenException ex) {
        log.warn("Handling EmailIsAlreadyTakenException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(error);
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

    @ExceptionHandler(NoSuchRoleException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoSuchRoleException(NoSuchRoleException ex) {
        log.warn("Handling NoSuchRoleException.");

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
