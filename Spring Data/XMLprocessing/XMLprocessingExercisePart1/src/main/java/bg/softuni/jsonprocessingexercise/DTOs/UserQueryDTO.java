package bg.softuni.jsonprocessingexercise.DTOs;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
public class UserQueryDTO implements Serializable {

    @Expose
    private String firstName;

    @Expose
    @NotNull
    private String lastName;

    @Expose
    private Set<SoldProductQueryDTO> soldProducts;
}
