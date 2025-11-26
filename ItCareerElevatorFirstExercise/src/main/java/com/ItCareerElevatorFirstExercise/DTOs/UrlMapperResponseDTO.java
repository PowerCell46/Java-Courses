package com.ItCareerElevatorFirstExercise.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UrlMapperResponseDTO {

    private String URL;

    private String shortenedUrl;
}
