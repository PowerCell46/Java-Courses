package bg.softuni.jsonprocessingexercise.DTOs;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class SoldProductQueryDTO implements Serializable {

    @Expose
    private String name;

    @Expose
    private BigDecimal price;

    @Expose
    private String buyerFirstName;

    @Expose
    @NotNull
    private String buyerLastName;
}
