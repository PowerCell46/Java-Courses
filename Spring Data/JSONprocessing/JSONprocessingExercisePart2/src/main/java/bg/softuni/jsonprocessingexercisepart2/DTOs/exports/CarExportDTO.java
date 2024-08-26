package bg.softuni.jsonprocessingexercisepart2.DTOs.exports;

import com.google.gson.annotations.Expose;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class CarExportDTO implements Serializable {

    @Expose
    private long Id;

    @Expose
    private String Make;

    @Expose
    private String Model;

    @Expose
    private long TravelledDistance;
}
