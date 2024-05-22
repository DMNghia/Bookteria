package org.nghia.identityservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class RegisterRequest {
    @NotEmpty(message = "Username not empty")
    @Email(message = "Email format is not correct")
    private String username;

    @NotEmpty(message = "Password not empty")
    @Size(min = 5, max = 30, message = "Password must be at least 5 characters and at most 30 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$", message = "Password must include at least 1 uppercase, 1 lowercase and 1 digit")
    private String password;

    @NotEmpty(message = "Firstname not empty")
    private String firstName;

    @NotEmpty(message = "Last name not empty")
    private String lastName;

    @NotNull(message = "Please enter your date of birth")
    private Date birthDate;
}
