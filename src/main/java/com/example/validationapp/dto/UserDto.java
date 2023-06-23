package com.example.validationapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
public class UserDto {
    @NotEmpty
    @Size(min = 4)
    private String username;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    private String password;

    @NotNull
    private Date dateOfBirth;
    public String getErrorMessage() {
        StringBuilder errorMessage = new StringBuilder();
        if (username.isEmpty() || username.length() < 4) {
            errorMessage.append("Username: must not be empty and should have at least 4 characters. ");
        }
        if (email.isEmpty() || !email.matches("^(.+)@(.+)$")) {
            errorMessage.append("Email: must not be empty and should be a valid email address. ");
        }
        if (password.isEmpty() || !password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
            errorMessage.append("Password: must not be empty and should be a strong password. ");
        }
        if (!isDateOfBirthValid(dateOfBirth)) {
            errorMessage.append("Date of Birth: must not be empty and should be at least 16 years ago. ");
        }
        return errorMessage.toString().trim();
    }
    private boolean isDateOfBirthValid(Date dateOfBirth) {
        Date currentDate = new Date();
        long ageInMillis = 16L * 365 * 24 * 60 * 60 * 1000; // 16 years in milliseconds
        return dateOfBirth != null && dateOfBirth.before(new Date(currentDate.getTime() - ageInMillis));
    }
}