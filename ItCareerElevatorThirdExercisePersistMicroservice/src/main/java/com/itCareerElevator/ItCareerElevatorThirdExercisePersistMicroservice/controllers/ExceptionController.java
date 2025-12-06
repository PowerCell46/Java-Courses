package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.controllers;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.ErrorResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions.NoSuchTweetException;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions.NoSuchUserException;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions.TweetAlreadyLikedException;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions.UserNotFollowingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<ErrorResponseDTO> handleError(NoSuchUserException ex) {
        log.warn("Handling NoSuchUserException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFollowingException.class)
    public ResponseEntity<ErrorResponseDTO> handleError(UserNotFollowingException ex) {
        log.warn("Handling UserNotFollowingException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchTweetException.class)
    public ResponseEntity<ErrorResponseDTO> handleError(NoSuchTweetException ex) {
        log.warn("Handling NoSuchTweetException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TweetAlreadyLikedException.class)
    public ResponseEntity<ErrorResponseDTO> handleError(TweetAlreadyLikedException ex) {
        log.warn("Handling TweetAlreadyLikedException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
