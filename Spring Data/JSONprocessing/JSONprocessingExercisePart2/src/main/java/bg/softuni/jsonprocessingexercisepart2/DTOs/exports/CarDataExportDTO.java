package bg.softuni.jsonprocessingexercisepart2.DTOs.exports;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
public class CarDataExportDTO implements Serializable {

    @Expose
    private String make;

    @Expose
    private String model;

    @Expose
    private long TravelledDistance;

    @Expose
    private List<PartDataExportDTO> parts;
}
