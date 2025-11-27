package be.skenteridis.movieapi.controller;

import be.skenteridis.movieapi.dto.LoginDTO;
import be.skenteridis.movieapi.dto.RegisterDTO;
import be.skenteridis.movieapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDTO dto) {
        service.register(dto);
        return ResponseEntity.ok(Map.of("success", true, "message", "Account registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO dto) {
        return ResponseEntity.ok(service.login(dto));
    }
}
