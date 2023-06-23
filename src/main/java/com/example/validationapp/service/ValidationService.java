package com.example.validationapp.service;

import com.example.validationapp.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class ValidationService {
    public boolean verifyUser(UserDto userDto) {
        return isUsernameValid(userDto.getUsername())
                && isEmailValid(userDto.getEmail())
                && isPasswordValid(userDto.getPassword())
                && isDateOfBirthValid(userDto.getDateOfBirth());
    }
    private boolean isUsernameValid(String username) {
        return !username.isEmpty() && username.length() >= 4;
    }
    private boolean isEmailValid(String email) {
        return !email.isEmpty() && email.matches("^(.+)@(.+)$");
    }
    private boolean isPasswordValid(String password) {
        return !password.isEmpty() && password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    }
    private boolean isDateOfBirthValid(Date dateOfBirth) {
        Date currentDate = new Date();
        long ageInMillis = 16L * 365 * 24 * 60 * 60 * 1000;
        return dateOfBirth != null && dateOfBirth.before(new Date(currentDate.getTime() - ageInMillis));
    }
}
