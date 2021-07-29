package com.example.backend.model.binding;

import com.example.backend.model.validators.FieldMatch;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@FieldMatch(
        first = "password",
        second = "confirmPassword"
)
public class UserRegisterBindingModel {
    private LocalDate dateOfBirth;
    private String email;
    private String password;
    private String confirmPassword;

    public UserRegisterBindingModel() {
    }

    @NotNull(message = "Date of birth field cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Date of birth cannot be in the present or future")
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    @NotBlank(message = "Password field cannot be empty")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@!#$%^&+=])(?=\\S+$).{8,}", message = "Password must be at least 8 characters long, including: 1 uppercase, 1 lowercase, 1 number, 1 special character")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
