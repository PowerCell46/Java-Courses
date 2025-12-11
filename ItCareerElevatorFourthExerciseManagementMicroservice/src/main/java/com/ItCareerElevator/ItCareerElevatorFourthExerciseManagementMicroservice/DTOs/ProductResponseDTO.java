package com.ItCareerElevator.ItCareerElevatorFourthExerciseManagementMicroservice.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductResponseDTO {

    private String id;

    private String bgName;

    private String enName;

    private String bgDescription;

    private String enDescription;

    private String producerName;

    private BigDecimal price;

    private Integer inStockQuantity;
}
