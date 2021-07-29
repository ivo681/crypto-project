package com.example.backend.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

public class UserLoginBindingModel {
    private String email;
    private String password;

    public UserLoginBindingModel() {
    }

    @NotBlank(message = "Email field cannot be empty")
    @Email(message = "Please enter a valid email address")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotBlank(message = "Password field cannot be empty")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@!#$%^&+=])(?=\\S+$).{8,}", message = "Password must be at least 8 characters long, including: 1 uppercase, 1 lowercase, 1 number, 1 special character")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
