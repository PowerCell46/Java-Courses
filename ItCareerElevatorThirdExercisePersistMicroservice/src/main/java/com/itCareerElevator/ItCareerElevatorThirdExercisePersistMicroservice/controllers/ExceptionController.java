package com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.controllers;

import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.DTOs.common.ErrorResponseDTO;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions.tweetRelated.NoSuchTweetException;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions.userRelated.NoSuchUserException;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions.tweetRelated.TweetAlreadyLikedException;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions.tweetRelated.TweetNotLikedException;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions.userRelated.UserCannotFollowThemselvesException;
import com.itCareerElevator.ItCareerElevatorThirdExercisePersistMicroservice.exceptions.userRelated.UserNotFollowingException;
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

    @ExceptionHandler(UserCannotFollowThemselvesException.class)
    public ResponseEntity<ErrorResponseDTO> handleError(UserCannotFollowThemselvesException ex) {
        log.warn("Handling UserCannotFollowThemselvesException.");

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

    @ExceptionHandler(TweetNotLikedException.class)
    public ResponseEntity<ErrorResponseDTO> handleError(TweetNotLikedException ex) {
        log.warn("Handling TweetNotLikedException.");

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
