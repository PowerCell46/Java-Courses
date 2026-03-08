package com.ItCareerElevatorSixthExercise.DTOs.response;

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

    private String id; // snowflakeId

    private String name; // In the default language

    private BigDecimal price;

    private Integer inStockQuantity;

    private String imageUrl;
}
