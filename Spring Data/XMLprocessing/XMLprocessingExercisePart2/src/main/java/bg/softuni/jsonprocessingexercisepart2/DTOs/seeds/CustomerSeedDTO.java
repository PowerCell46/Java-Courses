package bg.softuni.jsonprocessingexercisepart2.DTOs.seeds;

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
public class CustomerSeedDTO implements Serializable {

    @Expose
    private String name;

    @Expose
    private String birthDate;

    @Expose
    private boolean isYoungDriver;
}
