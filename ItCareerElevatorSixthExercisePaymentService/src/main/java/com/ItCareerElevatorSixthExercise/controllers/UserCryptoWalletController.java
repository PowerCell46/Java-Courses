package com.ItCareerElevatorSixthExercise.controllers;

import com.ItCareerElevatorSixthExercise.DTOs.request.UpdateUserWalletRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.UserCryptoWalletRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserCryptoWalletResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserResponseDTO;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserCryptoWalletService;
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
@RequestMapping("/api/users-wallets/crypto")
public class UserCryptoWalletController {

    private final UserCryptoWalletService userCryptoWalletService;

    @GetMapping("/{id}")
    public ResponseEntity<UserCryptoWalletResponseDTO> getUserCryptoWalletAddress(@PathVariable String id) {
        log.info("---> GET request on api/users-wallets/crypto/{}.", id);

        var responseDTO = userCryptoWalletService.getWalletAddress(id);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCryptoWalletRequestDTO requestDTO) {
        log.info("---> POST request on api/users-wallets/crypto for user with id {}.", requestDTO.getId());

        var responseDTO = userCryptoWalletService.setWalletAddress(requestDTO);

        URI location = URI.create(String.format("/api/users-wallets/crypto/%s", responseDTO.getId()));
        return ResponseEntity.created(location).body(new UserResponseDTO(requestDTO.getId()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserCryptoWalletResponseDTO> updateUser(
            @PathVariable String id,
            @RequestBody UpdateUserWalletRequestDTO requestDTO
    ) {
        log.info("---> PATCH request on api/users-wallets/crypto/{}.", id);

        var responseDTO = userCryptoWalletService.setWalletAddress(new UserCryptoWalletRequestDTO(id, requestDTO.getWalletAddress()));

        return ResponseEntity.ok(responseDTO);
    }
}
