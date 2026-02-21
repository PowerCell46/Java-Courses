package com.ItCareerElevatorSixthExercise.controllers;

import com.ItCareerElevatorSixthExercise.DTOs.request.UserRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.response.UserResponseDTO;
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

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO requestDTO) {
        log.info("---> POST request on api/users for user with id {}.", requestDTO.getId());

        UserResponseDTO responseDTO = userService.setWalletAddress(requestDTO);

        return ResponseEntity.created(null).body(responseDTO); // TODO: URL?
    }

//    @PatchMapping("/{id}") // TODO: If you use /id you don't have to pass it in the request body
//    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String id, @RequestBody UserRequestDTO requestDTO) {
//        log.info("---> PATCH request on api/users/{}.", requestDTO.getId());
//
//        UserResponseDTO responseDTO = userService.setWalletAddress(requestDTO);
//
//        return ResponseEntity.ok(responseDTO);
//    }
}
