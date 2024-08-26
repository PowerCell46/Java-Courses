package bg.softuni.jsonprocessingexercisepart2.DTOs.exports;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class SupplierExportDTO implements Serializable {

    @Expose
    private long Id;

    @Expose
    private String Name;

    @Expose
    private int partsCount;
}
