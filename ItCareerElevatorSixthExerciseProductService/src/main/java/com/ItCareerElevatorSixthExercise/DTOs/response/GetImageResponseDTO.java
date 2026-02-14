package com.ItCareerElevatorSixthExercise.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetImageResponseDTO {

    private String contentType;

    private String name;

    private String base64;
}
