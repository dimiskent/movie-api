package be.skenteridis.movieapi.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterDTO {
    @NotBlank(message = "Name can't be blank!")
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank(message = "Email can't be blank!")
    @Column(nullable = false, unique = true)
    @Email(message = "Invalid email!")
    private String email;

    @NotBlank(message = "Password can't be blank!")
    @Size(min = 10, max = 30, message = "Password must be between 10 and 30 characters")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
