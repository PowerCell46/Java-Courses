package bg.softuni.jsonprocessingexercisepart2.DTOs.exports;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class CustomerExportDTO implements Serializable {

    @Expose
    private long Id;

    @Expose
    private String Name;

    @Expose
    private boolean IsYoungDriver;

    @Expose
    private List<SaleExportDTO> Sales;
}
