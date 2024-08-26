package bg.softuni.jsonprocessingexercisepart2.DTOs.exports;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
public class SaleExportDTO implements Serializable {

    @Expose
    private int Discount;

    @Expose
    private String CarName;
}
