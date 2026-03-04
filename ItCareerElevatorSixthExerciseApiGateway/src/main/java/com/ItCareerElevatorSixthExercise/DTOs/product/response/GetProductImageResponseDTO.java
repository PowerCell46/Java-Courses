package com.ItCareerElevatorSixthExercise.DTOs.product.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetProductImageResponseDTO {

    private String name;

    private String contentType;

    private String base64;
}
