package bg.softuni.xmlprocessingexercise.DTOs;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
public class CategoryQueryDTO implements Serializable {

    @Expose
    private String category;

    @Expose
    private long productsCount;

    @Expose
    private BigDecimal averagePrice;

    @Expose
    private BigDecimal totalRevenue;
}

