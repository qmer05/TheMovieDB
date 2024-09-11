package app;

import app.dtos.MovieDTO;
import app.services.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        MovieService movieService = new MovieService(objectMapper);

        //MovieDTO movieDTO = movieService.getMovieById(139);
        // System.out.printf("Overview: %s%n", movieDTO.getOverview());

        List<MovieDTO> movies = movieService.getMoviesByRating(8.99, 9.0);
        movies.forEach(System.out::println);
    }
}