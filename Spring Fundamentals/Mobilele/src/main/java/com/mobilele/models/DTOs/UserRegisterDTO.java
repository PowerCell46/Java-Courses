package com.mobilele.models.DTOs;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRegisterDTO {

    @NotEmpty
    @Size(min = 5, max = 20)
    private String firstName;

    @NotEmpty
    @Size(min = 5, max = 20)
    private String lastName;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
