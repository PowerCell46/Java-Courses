package bg.softuni.jsonprocessingexercisepart2.DTOs.exports;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class SalesStatisticsExportDTO implements Serializable {

    @Expose
    private CarStatisticsExportDTO car;

    @Expose
    private String customerName;

    @Expose
    private double Discount;

    @Expose
    private BigDecimal price;

    @Expose
    private BigDecimal priceWithDiscount;
}
