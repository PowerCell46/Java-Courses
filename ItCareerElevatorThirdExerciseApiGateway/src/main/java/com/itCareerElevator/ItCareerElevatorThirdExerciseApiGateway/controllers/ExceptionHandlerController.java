package com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.controllers;

import com.itCareerElevator.ItCareerElevatorThirdExerciseApiGateway.DTOs.ErrorResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.security.SignatureException;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlerController {

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
}
