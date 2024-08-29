package bg.softuni.xmlprocessingexercise.DTOs;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProductNextQueryDTO implements Serializable {

    @Expose
    private String name;

    @Expose
    private BigDecimal price;
}
