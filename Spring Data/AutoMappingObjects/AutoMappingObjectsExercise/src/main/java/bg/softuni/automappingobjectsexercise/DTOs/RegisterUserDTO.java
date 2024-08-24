package bg.softuni.automappingobjectsexercise.DTOs;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterUserDTO {

    private String email;

    private String password;

    private String confirmPassword;

    private String fullName;
}
