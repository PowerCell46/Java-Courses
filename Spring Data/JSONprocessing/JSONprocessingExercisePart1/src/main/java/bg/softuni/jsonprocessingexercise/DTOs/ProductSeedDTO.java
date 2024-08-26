package bg.softuni.jsonprocessingexercise.DTOs;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
public class ProductSeedDTO implements Serializable {

    @Expose
    @NotNull
    @Size(min = 3)
    private String name;

    @Expose
    @NotNull
    @Min(0)
    private BigDecimal price;
}
