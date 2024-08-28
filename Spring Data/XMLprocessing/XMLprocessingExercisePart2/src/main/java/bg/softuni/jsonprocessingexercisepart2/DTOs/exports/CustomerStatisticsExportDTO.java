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
public class CustomerStatisticsExportDTO implements Serializable {

    @Expose
    private String fullName;

    @Expose
    private int boughtCars;

    @Expose
    private BigDecimal spendMoney;
}
