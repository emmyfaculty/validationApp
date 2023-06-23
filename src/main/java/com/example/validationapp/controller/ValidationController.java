package com.example.validationapp.controller;

import com.example.validationapp.dto.TokenDto;
import com.example.validationapp.dto.UserDto;
import com.example.validationapp.service.ValidationService;
import com.example.validationapp.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class ValidationController {

    private final ValidationService validationService;

    @PostMapping("/validate")
    public ResponseEntity<?> validateFields(@Valid @RequestBody UserDto userDto) {

        boolean isAllValid = validationService.verifyUser(userDto);

        if (!isAllValid) {
            return new ResponseEntity<>("Validation failed: " + userDto.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        // Generate and return a signed JWT
        String jwt = JwtUtils.generateJWT(userDto.getUsername());
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyToken(@RequestBody TokenDto tokenDto) {
        // Verify the token and return verification status
        boolean isTokenValid = JwtUtils.verifyJWT(tokenDto.getToken());

        if (isTokenValid) {
            return ResponseEntity.ok("Verification pass");
        } else {
            return ResponseEntity.ok("Verification fails");
        }
    }

}


