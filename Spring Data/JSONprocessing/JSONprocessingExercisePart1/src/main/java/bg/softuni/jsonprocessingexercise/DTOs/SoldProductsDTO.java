package bg.softuni.jsonprocessingexercise.DTOs;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class SoldProductsDTO implements Serializable {

    @Expose
    private long count;

    @Expose
    private List<ProductNextQueryDTO> products;
}
