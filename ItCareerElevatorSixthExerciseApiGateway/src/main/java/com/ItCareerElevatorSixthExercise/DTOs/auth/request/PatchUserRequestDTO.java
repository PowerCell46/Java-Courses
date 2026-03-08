package com.ItCareerElevatorSixthExercise.DTOs.auth.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchUserRequestDTO {

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{5,20}$",
            message = "Username must be 5-20 characters, contain letters and digits, with at least one uppercase letter and one digit."
    )
    private String username;

    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email must be a valid address (example: user@example.com)."
    )
    private String email;

    /* @Pattern(
            regexp = "^(0x[a-fA-F0-9]{40})?$",
            message = "Wallet address must be a valid Ethereum address (example: 0x742d35Cc6634C0532925a3b844Bc454e4438f44e)."
    )
    private String walletAddress; */
}
