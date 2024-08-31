package bg.softuni.mvcworkshop.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserRegisterDTO {

    private String username;

    private String password;

    private String confirmPassword;

    private String email;
}
