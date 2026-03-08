package com.ItCareerElevatorSixthExercise.controllers;

import com.ItCareerElevatorSixthExercise.DTOs.auth.request.DepositAmountRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.PatchUserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.ResetPasswordRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.request.RegisterUserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.AlterUserResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.DepositAmountResponseDTO;
import com.ItCareerElevatorSixthExercise.entities.User;
import com.ItCareerElevatorSixthExercise.services.interfaces.PaymentService;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final PaymentService paymentService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> register(@RequestBody RegisterUserRequestDTO requestDTO) {
        log.info("---> POST request on api/users with username: {}.", requestDTO.getUsername());

        User registeredUser = userService.register(requestDTO);

        return ResponseEntity.created(null).body(registeredUser); // TODO: URL
    }

    // TODO: GetMapping on /userId for fetching the user details

    @PatchMapping("/{userId}")
    public ResponseEntity<AlterUserResponseDTO> updateUserDetails(
            @PathVariable String userId,
            @RequestBody PatchUserRequestDTO requestDTO
    ) {
        log.info("---> PATCH request on api/users/{}.", userId);

        var responseDTO = userService.updateFields(userId, requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/reset-password/{userId}")
    public ResponseEntity<AlterUserResponseDTO> resetPassword(
            @PathVariable String userId,
            @RequestBody ResetPasswordRequestDTO requestDTO
    ) {
        log.info("---> PATCH request on api/users/reset-password/{}.", userId);

        var responseDTO = userService.resetPassword(userId, requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/deposit")
    public ResponseEntity<DepositAmountResponseDTO> depositAmount(@RequestBody DepositAmountRequestDTO requestDTO) {
        log.info("---> POST request on api/users/deposit for user with id {}.", requestDTO.getId());

        var responseDTO = paymentService.depositAmount(requestDTO);

        return ResponseEntity.ok(responseDTO);
    }
}
