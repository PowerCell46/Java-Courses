package com.ItCareerElevatorFirstExercise.controllers;

import com.ItCareerElevatorFirstExercise.DTOs.UrlMapperCreateRequestDTO;
import com.ItCareerElevatorFirstExercise.DTOs.UrlMapperResponseDTO;
import com.ItCareerElevatorFirstExercise.exceptions.InvalidAliasException;
import com.ItCareerElevatorFirstExercise.services.interfaces.UrlMapperService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/url-mapper")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UrlMapperController {

    private final UrlMapperService urlMapperService;

    @PostMapping(value = "")
    public ResponseEntity<UrlMapperResponseDTO> shortenUrl(@Valid @RequestBody UrlMapperCreateRequestDTO requestDTO) {
        log.info("--- POST request for URL: {}", requestDTO.getURL());

        String shortenedUrl = urlMapperService.convertUrlToAlias(requestDTO.getURL());

        UrlMapperResponseDTO responseDTO = new UrlMapperResponseDTO(requestDTO.getURL(), shortenedUrl);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping(value = "/{alias}")
    public ResponseEntity<Void> redirectShortenedUrL(
            @PathVariable
            @Size(min = 11, max = 11, message = "Alias must be 11 characters long.")
            @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Alias must contain only alphanumeric characters.")
            String alias
    ) {
        log.info("--- GET request for alias: {}", alias);

        Optional<String> redirectUrl = urlMapperService.convertAliasToUrl(alias);

        if (redirectUrl.isPresent()) {
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                    .header(HttpHeaders.LOCATION, redirectUrl.get())
                    .build();
        }

        throw new InvalidAliasException(String.format("No such alias [%s] exists.", alias));
    }
}
