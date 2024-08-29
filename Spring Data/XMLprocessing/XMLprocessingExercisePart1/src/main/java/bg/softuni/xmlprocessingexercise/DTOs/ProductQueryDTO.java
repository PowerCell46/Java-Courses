package bg.softuni.xmlprocessingexercise.DTOs;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductQueryDTO implements Serializable {

    @Expose
    private String name;

    @Expose
    private BigDecimal price;

    @Expose
    private String seller;
}
