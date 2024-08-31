package bg.softuni.jsonprocessingexercise.DTOs;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@Builder
public class UserSeedDTO implements Serializable {

    @Expose
    private String firstName;

    @Expose
    @NotNull
    @Size(min = 3)
    private String lastName;

    @Expose
    private int age;
}
