package com.ItCareerElevator.ItCareerElevatorFourthExerciseApiGateway.DTOs.products.response;

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

    private String name;

    private String description;

    private String producerName;

    private BigDecimal price;

    private Integer inStockQuantity;
}
