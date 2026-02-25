package com.ItCareerElevatorSixthExercise.controllers;

import com.ItCareerElevatorSixthExercise.DTOs.request.UpdateUserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.UserDepositRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.request.UserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.GetUserWalletAddressResponseDTO;
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

    // TODO: endpoint for getting the account balance

    @PostMapping("/deposit")
    public ResponseEntity<UserDepositResponseDTO> initializeUser(@RequestBody UserDepositRequestDTO requestDTO) {
        log.info("---> POST request on api/users/deposit for user with id {}.", requestDTO.getUserId());

        var responseDTO = userWalletService.initializeUser(requestDTO);
        return ResponseEntity.created(null).body(responseDTO); // TODO: URL
    }

    @PatchMapping("/deposit/{id}")
    public ResponseEntity<UserDepositResponseDTO> depositAmount(
            @PathVariable String id, @RequestBody UserDepositRequestDTO requestDTO
    ) {
        log.info("---> PATCH request on api/users/deposit/{}.", requestDTO.getUserId());

        var responseDTO = userWalletService.processDeposit(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserWalletAddressResponseDTO> getUserWalletAddress(@PathVariable String id) {
        log.info("---> GET request on api/users/{}.", id);

        var responseDTO = userCryptoService.getWalletAddress(id);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO requestDTO) {
        log.info("---> POST request on api/users for user with id {}.", requestDTO.getId());

        UserResponseDTO responseDTO = userCryptoService.setWalletAddress(requestDTO);

        URI location = URI.create(String.format("/api/users/%s", responseDTO.getId()));
        return ResponseEntity.created(location).body(responseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable String id,
            @RequestBody UpdateUserRequestDTO requestDTO
    ) {
        log.info("---> PATCH request on api/users/{}.", id);

        UserResponseDTO responseDTO = userCryptoService
                .setWalletAddress(new UserRequestDTO(id, requestDTO.getWalletAddress()));

        return ResponseEntity.ok(responseDTO);
    }
}
