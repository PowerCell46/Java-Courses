package bg.softuni.jsonprocessingexercise.DTOs;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class CategorySeedDTO implements Serializable {

    @Expose
    @NotNull
    @Size(min = 3, max = 15)
    private String name;
}
