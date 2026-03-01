package com.ItCareerElevatorSixthExercise.controllers;

import com.ItCareerElevatorSixthExercise.DTOs.request.UpdateUserBalanceRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.UserLocalWalletRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserLocalWalletResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserResponseDTO;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserLocalWalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users-wallets/local")
public class UserLocalWalletController {

    private final UserLocalWalletService userLocalWalletService;

    @GetMapping("/{id}")
    public ResponseEntity<UserLocalWalletResponseDTO> getUserBalance(@PathVariable String id) {
        log.info("---> GET request on api/users-wallets/local/{}.", id);

        var responseDTO = userLocalWalletService.getUserById(id);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserLocalWalletRequestDTO requestDTO) {
        log.info("---> POST request on api/users-wallets/local for user with id {}.", requestDTO.getId());

        var responseDTO = userLocalWalletService.initializeUser(requestDTO);

        URI location = URI.create(String.format("/api/users-wallets/local/%s", responseDTO.getId()));
        return ResponseEntity.created(location).body(responseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserLocalWalletResponseDTO> updateUser(
            @PathVariable String id,
            @RequestBody UpdateUserBalanceRequestDTO requestDTO
    ) {
        log.info("---> PATCH request on api/users-wallets/local/{}.", id);

        var responseDTO = userLocalWalletService.processDeposit(id, requestDTO.getAmount());

        return ResponseEntity.ok(responseDTO);
    }
}
