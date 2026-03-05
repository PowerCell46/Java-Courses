package com.ItCareerElevatorSixthExercise.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetProductResponseDTO {

    private String id; // snowflakeId

    private List<String> nameTranslations;

    private List<String> descriptionTranslations;

    private String manufacturerName;

    private Integer inStockQuantity;

    private BigDecimal price;

    private String imageUrl;
}
