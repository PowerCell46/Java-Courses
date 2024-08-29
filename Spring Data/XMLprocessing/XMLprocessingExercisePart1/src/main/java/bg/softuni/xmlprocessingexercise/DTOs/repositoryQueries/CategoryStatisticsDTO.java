package bg.softuni.xmlprocessingexercise.DTOs.repositoryQueries;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryStatisticsDTO {

    private String name;

    private long numberOfProducts;

    private BigDecimal averagePrice;

    private BigDecimal totalRevenue;
}

