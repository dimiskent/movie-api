package be.skenteridis.movieapi.service;

import be.skenteridis.movieapi.dto.MovieRequestDTO;
import be.skenteridis.movieapi.dto.MovieResponseDTO;
import be.skenteridis.movieapi.exception.InvalidCredentialsException;
import be.skenteridis.movieapi.exception.MovieNotFoundException;
import be.skenteridis.movieapi.mapper.MovieMapper;
import be.skenteridis.movieapi.model.Movie;
import be.skenteridis.movieapi.model.Role;
import be.skenteridis.movieapi.model.User;
import be.skenteridis.movieapi.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository repository;
    private final MovieMapper mapper;
    public MovieService(MovieRepository repository) {
        this.repository = repository;
        mapper = new MovieMapper();
    }

    public List<MovieResponseDTO> getMovies() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public MovieResponseDTO getMovieById(Long id) {
        return repository.findById(id).map(mapper::toDTO).orElseThrow(() -> new MovieNotFoundException("Movie not found!"));
    }

    public MovieResponseDTO addMovie(MovieRequestDTO dto, User user) {
        Movie movie = mapper.toMovie(dto);
        movie.setUser(user);
        return mapper.toDTO(repository.save(movie));
    }

    private void ensureOwnership(Movie movie, User user) {
        if(user.getRole() == Role.USER && !movie.getUser().getId().equals(user.getId()))
            throw new InvalidCredentialsException("No permission to modify this movie");
    }

    public MovieResponseDTO updateMovie(Long id, MovieRequestDTO dto, User user) {
        Movie movie = repository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie not found!"));

        ensureOwnership(movie, user);

        movie.setTitle(dto.getTitle());
        movie.setDirector(dto.getDirector());
        movie.setGenre(dto.getGenre());
        movie.setYear(dto.getYear());

        return mapper.toDTO(repository.save(movie));
    }

    public void deleteMovie(Long id, User user) {
        Movie movie = repository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie not found!"));
        ensureOwnership(movie, user);
        repository.delete(movie);
    }
}
