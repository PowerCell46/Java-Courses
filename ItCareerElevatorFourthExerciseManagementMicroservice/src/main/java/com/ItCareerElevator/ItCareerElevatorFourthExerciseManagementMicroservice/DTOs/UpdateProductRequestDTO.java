package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class UpdateProductRequestDTO {

    private String bgName;

    private String enName;

    private String bgDescription;

    private String enDescription;

    private String producerName;

    private BigDecimal price;

    private Integer inStockQuantity;
}
