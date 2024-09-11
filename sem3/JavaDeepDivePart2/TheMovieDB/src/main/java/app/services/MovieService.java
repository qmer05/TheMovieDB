package app.services;

import app.dtos.MovieDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class MovieService {

    private final ObjectMapper objectMapper;

    public MovieService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<MovieDTO> getMoviesByRating(double minRating, double maxRating) {

        List<MovieDTO> movies = new ArrayList<>();
        int currentPage = 1;
        int totalPages;

        try {
            do {
                StringBuilder builder = new StringBuilder("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=")
                        .append(currentPage)
                        .append("&sort_by=popularity.desc&vote_average.gte=")
                        .append(minRating)
                        .append("&vote_average.lte=")
                        .append(maxRating)
                        .append("&api_key=")
                        .append(System.getenv("api_key"));

                HttpResponse<String> response = HttpClientService.fetchData(builder.toString());
                JsonNode json = objectMapper.readTree(response.body());
                JsonNode results = json.get("results");

                totalPages = json.get("total_pages").asInt();
                currentPage++;

                System.out.printf("Loading: %d%%%n", (int)Math.floor((double)currentPage / totalPages * 100));

                for (JsonNode node : results) {
                    MovieDTO movieDTO = objectMapper.treeToValue(node, MovieDTO.class);
                    movies.add(movieDTO);
                }
            } while (currentPage <= totalPages);

            return movies;
        } catch (URISyntaxException | IOException | InterruptedException | RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public MovieDTO getMovieById(Integer id) {
        try {
            StringBuilder builder = new StringBuilder("https://api.themoviedb.org/3/movie/")
                    .append(id)
                    .append("?api_key=")
                    .append(System.getenv("api_key"));

            HttpResponse<String> response = HttpClientService.fetchData(builder.toString());
            String json = response.body();

            return objectMapper.readValue(json, MovieDTO.class);
        } catch (URISyntaxException | IOException | InterruptedException | RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
