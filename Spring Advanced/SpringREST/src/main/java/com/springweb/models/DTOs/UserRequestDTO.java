package com.springweb.models.DTOs;


import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequestDTO {

    private int id;

    private String firstname;

    private String lastname;

    private String email;

    private LocalDate birthDate;

    private String phone;

    private String website;
}
