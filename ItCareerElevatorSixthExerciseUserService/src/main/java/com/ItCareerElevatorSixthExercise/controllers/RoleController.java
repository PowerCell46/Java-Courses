package com.ItCareerElevatorSixthExercise.controllers;

import com.ItCareerElevatorSixthExercise.DTOs.auth.request.AssignRolesRequestDTO;
import com.ItCareerElevatorSixthExercise.DTOs.auth.response.AlterUserResponseDTO;
import com.ItCareerElevatorSixthExercise.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {

    private final UserService userService;

    @PostMapping("/assign-to-user")
    public ResponseEntity<AlterUserResponseDTO> assignRolesToUser(@RequestBody AssignRolesRequestDTO requestDTO) {
        log.info("---> POST request on api/roles/assign-to-user for user with username {}.", requestDTO.getUsername());

        var responseDTO = userService.assignRolesToUser(requestDTO);

        return ResponseEntity.ok(responseDTO);
    }
}
