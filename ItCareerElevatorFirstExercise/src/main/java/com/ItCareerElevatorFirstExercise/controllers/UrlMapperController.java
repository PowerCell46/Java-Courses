package com.ItCareerElevatorFirstExercise.controllers;

import com.ItCareerElevatorFirstExercise.DTOs.UrlMapperCreateRequestDTO;
import com.ItCareerElevatorFirstExercise.DTOs.UrlMapperCreateResponseDTO;
import com.ItCareerElevatorFirstExercise.services.interfaces.UrlMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/url-mapper")
@RequiredArgsConstructor
public class UrlMapperController {

    private final UrlMapperService urlMapperService;

    @PostMapping(value = "/")
    public ResponseEntity<UrlMapperCreateResponseDTO> shortenUrl(@RequestBody UrlMapperCreateRequestDTO requestDTO) {

        return ResponseEntity.ok(new UrlMapperCreateResponseDTO("2h8xk6td"));
    }
}
