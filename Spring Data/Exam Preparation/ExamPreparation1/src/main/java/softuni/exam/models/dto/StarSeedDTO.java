package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.enums.StarType;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class StarSeedDTO implements Serializable {

    @Expose
    private String description;

    @Expose
    private double lightYears;

    @Expose
    private String name;

    @Setter
    @Expose
    private StarType starType;

    @Setter
    @Expose
    private long constellation;

    private void setDescription(String description) {
        if (description.length() < 6) {
            throw new IllegalArgumentException();
        }
        this.description = description;
    }

    private void setLightYears(double lightYears) {
        if (lightYears <= 0) {
            throw new IllegalArgumentException();
        }
        this.lightYears = lightYears;
    }

    private void setName(String name) {
        if (name.length() < 2 || name.length() > 30) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public StarSeedDTO(String description, double lightYears, String name, StarType starType, long constellation) {
        this.setDescription(description);
        this.setLightYears(lightYears);
        this.setName(name);
        this.setStarType(starType);
        this.setConstellation(constellation);
    }
}
