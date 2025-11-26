package com.ItCareerElevatorFirstExercise.controllers;

import com.ItCareerElevatorFirstExercise.DTOs.UrlMapperCreateRequestDTO;
import com.ItCareerElevatorFirstExercise.DTOs.UrlMapperResponseDTO;
import com.ItCareerElevatorFirstExercise.services.interfaces.UrlMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/url-mapper")
@RequiredArgsConstructor
public class UrlMapperController {

    private final UrlMapperService urlMapperService;

    @PostMapping(value = "")
    public ResponseEntity<UrlMapperResponseDTO> shortenUrl(@RequestBody UrlMapperCreateRequestDTO requestDTO) {

        String shortenedUrl = urlMapperService.convertUrlToAlias(requestDTO.getURL());

        UrlMapperResponseDTO responseDTO = new UrlMapperResponseDTO(requestDTO.getURL(), shortenedUrl);

        return ResponseEntity.ok(responseDTO);
    }

//    @GetMapping(value = "")
    // ! If the entry exist, redirect with one of the 302/304?
//    public ResponseEntity<UrlMapperResponseDTO>
}
