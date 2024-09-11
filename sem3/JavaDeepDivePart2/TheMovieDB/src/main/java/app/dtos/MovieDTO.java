package app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO {
    private Integer id;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("original_language")
    private String originalLanguage;

    private String overview;

    @JsonProperty("release_date")
    private LocalDate releaseDate;

    private List<GenreDTO> genres;

    private Double vote_average;

    public String getReleaseYear() {
        return String.valueOf(releaseDate.getYear());
    }
}
