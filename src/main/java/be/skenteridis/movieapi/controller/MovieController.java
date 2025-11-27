package be.skenteridis.movieapi.controller;

import be.skenteridis.movieapi.dto.MovieRequestDTO;
import be.skenteridis.movieapi.dto.MovieResponseDTO;
import be.skenteridis.movieapi.exception.InvalidCredentialsException;
import be.skenteridis.movieapi.model.User;
import be.skenteridis.movieapi.service.MovieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movies")
@Valid
public class MovieController {
    private final MovieService service;
    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getMovies() {
        List<MovieResponseDTO> movies = service.getMovies();
        return movies.isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@Positive @PathVariable Long id) {
        return ResponseEntity.ok(service.getMovieById(id));
    }

    private void checkValidity(User user) {
        if (user == null) throw new InvalidCredentialsException("Invalid token, please reconnect.");
    }

    @PostMapping
    public ResponseEntity<?> addMovie(@AuthenticationPrincipal User user, @Valid @RequestBody MovieRequestDTO dto) {
        checkValidity(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addMovie(dto, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@Positive @PathVariable Long id, @AuthenticationPrincipal User user) {
        checkValidity(user);
        service.deleteMovie(id, user);
        return ResponseEntity.ok(Map.of("success", true, "message", "Movie deleted successfully!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@Positive @PathVariable Long id, @AuthenticationPrincipal User user, @Valid @RequestBody MovieRequestDTO dto) {
        checkValidity(user);
        return ResponseEntity.ok(service.updateMovie(id, dto, user));
    }
}
