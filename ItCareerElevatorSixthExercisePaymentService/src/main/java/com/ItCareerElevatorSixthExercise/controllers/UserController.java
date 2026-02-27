package com.ItCareerElevatorSixthExercise.controllers;

import com.ItCareerElevatorSixthExercise.DTOs.request.UpdateUserBalanceRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.UpdateUserWalletRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.UserDepositRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.UserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserWalletResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserDepositResponseDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserResponseDTO;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserCryptoService;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserWalletService;
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
@RequestMapping("/api/users")
public class UserController {

    private final UserWalletService userWalletService;
    private final UserCryptoService userCryptoService;

    @GetMapping("/balance/{id}")
    public ResponseEntity<UserDepositResponseDTO> getUserBalance(@PathVariable String id) {
        log.info("---> GET request on api/users/balance/{}.", id);

        var responseDTO = userWalletService.getUserById(id);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/deposit")
    public ResponseEntity<UserResponseDTO> initializeUser(@RequestBody UserDepositRequestDTO requestDTO) {
        log.info("---> POST request on api/users/deposit for user with id {}.", requestDTO.getUserId());

        var responseDTO = userWalletService.initializeUser(requestDTO);

        URI location = URI.create(String.format("/api/users/balance/%s", responseDTO.getId()));
        return ResponseEntity.created(location).body(responseDTO);
    }

    @PatchMapping("/deposit/{id}")
    public ResponseEntity<UserDepositResponseDTO> depositAmount(
            @PathVariable String id, @RequestBody UpdateUserBalanceRequestDTO requestDTO
    ) {
        log.info("---> PATCH request on api/users/deposit/{}.", id);

        var responseDTO = userWalletService
                .processDeposit(new UserDepositRequestDTO(id, requestDTO.getAmount()));

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/walletAddress/{id}")
    public ResponseEntity<UserWalletResponseDTO> getUserWalletAddress(@PathVariable String id) {
        log.info("---> GET request on api/users/walletAddress/{}.", id);

        var responseDTO = userCryptoService.getWalletAddress(id);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/wallet")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO requestDTO) {
        log.info("---> POST request on api/users/wallet for user with id {}.", requestDTO.getId());

        var responseDTO = userCryptoService.setWalletAddress(requestDTO);

        URI location = URI.create(String.format("/api/users/walletAddress/%s", responseDTO.getId()));
        return ResponseEntity.created(location).body(new UserResponseDTO(requestDTO.getId()));
    }

    @PatchMapping("/wallet/{id}")
    public ResponseEntity<UserWalletResponseDTO> updateUser(
            @PathVariable String id,
            @RequestBody UpdateUserWalletRequestDTO requestDTO
    ) {
        log.info("---> PATCH request on api/users/wallet/{}.", id);

        var responseDTO = userCryptoService
                .setWalletAddress(new UserRequestDTO(id, requestDTO.getWalletAddress()));

        return ResponseEntity.ok(responseDTO);
    }
}
