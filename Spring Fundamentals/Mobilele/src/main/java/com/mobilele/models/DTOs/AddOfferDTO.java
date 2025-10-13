package com.mobilele.models.DTOs;

import com.mobilele.models.enums.Engines;
import com.mobilele.models.enums.Transmissions;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class AddOfferDTO {

    @NotNull
    private String model;

    @Positive
    private BigDecimal price;

    @NotNull
    private Engines engineType;

    @NotNull
    private Transmissions transmissionType;

    @Min(1900)
    @Max(2024)
    private int manufacturingYear;

    @NotNull
    @PositiveOrZero
    private long mileage;

    @NotNull
    @NotEmpty
    @Size(min = 5)
    private String description;

    @NotEmpty
    private String imageUrl;
}
