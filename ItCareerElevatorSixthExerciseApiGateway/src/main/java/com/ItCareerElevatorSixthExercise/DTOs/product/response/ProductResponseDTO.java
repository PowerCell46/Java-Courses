package com.ItCareerElevatorSixthExercise.DTOs.product.response;

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

    private BigDecimal price;

    private Integer inStockQuantity;
}
