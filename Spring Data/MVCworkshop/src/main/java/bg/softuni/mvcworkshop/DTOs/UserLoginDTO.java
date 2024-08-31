package bg.softuni.mvcworkshop.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserLoginDTO {
    private static final int PASSWORD_MAX_LENGTH = 6;

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = PASSWORD_MAX_LENGTH)
    private String password;
}
