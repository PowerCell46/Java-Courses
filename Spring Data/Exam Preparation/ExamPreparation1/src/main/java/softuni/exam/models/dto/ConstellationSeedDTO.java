package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class ConstellationSeedDTO implements Serializable {

    @Expose
    private String name;

    @Expose
    private String description;

    public void setName(String name) {
        if (name.length() < 3 || name.length() > 20) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public void setDescription(String description) {
        if (description.length() < 5) {
            throw new IllegalArgumentException();
        }
        this.description = description;
    }

    public ConstellationSeedDTO(String name, String description) {
        this.setName(name);
        this.setDescription(description);
    }
}
