package be.skenteridis.movieapi.mapper;

import be.skenteridis.movieapi.dto.MovieRequestDTO;
import be.skenteridis.movieapi.dto.MovieResponseDTO;
import be.skenteridis.movieapi.model.Movie;

public class MovieMapper {
    public Movie toMovie(MovieRequestDTO dto) {
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle());
        movie.setDirector(dto.getDirector());
        movie.setGenre(dto.getGenre());
        movie.setYear(dto.getYear());
        return movie;
    }

    public MovieResponseDTO toDTO(Movie movie) {
        MovieResponseDTO dto = new MovieResponseDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDirector(movie.getDirector());
        dto.setGenre(movie.getGenre());
        dto.setYear(movie.getYear());
        return dto;
    }
}
