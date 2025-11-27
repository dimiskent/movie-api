package be.skenteridis.movieapi.dto;

import be.skenteridis.movieapi.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MovieRequestDTO {
    @NotBlank(message = "Title can't be blank!")
    private String title;

    @NotBlank(message = "Director can't be blank!")
    private String director;

    @NotBlank(message = "Genre can't be blank!")
    private String genre;

    @NotNull(message = "Year can't be null!")
    @Min(value = 1880, message = "Year must be reasonable (>1880)")
    @Max(value = 2100, message = "Year must be reasonable (<2100)")
    private Short year;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }
}
