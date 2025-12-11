package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CreateProductRequestDTO {

    private String bgName; // required

    private String enName; // required

    private String bgDescription; // required

    private String enDescription; // required

    private String producerName; // required

    private BigDecimal price; // required

    private Integer inStockQuantity; // optional
}
