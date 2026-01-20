package com.ItCareerElevatorSixthExercise.controllers;

import com.ItCareerElevatorSixthExercise.DTOs.response.ErrorResponseDTO;
import com.ItCareerElevatorSixthExercise.exceptions.InvalidSnowflakeIdException;
import com.ItCareerElevatorSixthExercise.exceptions.InvalidTranslationsException;
import com.ItCareerElevatorSixthExercise.exceptions.NoSuchLocaleException;
import com.ItCareerElevatorSixthExercise.exceptions.image.InvalidFileImageException;
import com.ItCareerElevatorSixthExercise.exceptions.image.ProcessImageFileException;
import com.ItCareerElevatorSixthExercise.exceptions.product.NoSuchProductException;
import com.ItCareerElevatorSixthExercise.exceptions.product.ProductAlreadyExistsException;
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

    @ExceptionHandler(InvalidFileImageException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidFileImageException(InvalidFileImageException ex) {
        log.warn("Handling InvalidFileImageException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(error);
    }

    @ExceptionHandler(ProcessImageFileException.class)
    public ResponseEntity<ErrorResponseDTO> handleProcessImageFileException(ProcessImageFileException ex) {
        log.warn("Handling ProcessImageFileException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(error);
    }

    @ExceptionHandler(InvalidTranslationsException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidTranslationsException(InvalidTranslationsException ex) {
        log.warn("Handling InvalidTranslationsException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(error);
    }

    @ExceptionHandler(NoSuchLocaleException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoSuchLocaleException(NoSuchLocaleException ex) {
        log.warn("Handling NoSuchLocaleException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
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

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleProductAlreadyExistsException(ProductAlreadyExistsException ex) {
        log.warn("Handling ProductAlreadyExistsException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(error);
    }
}
