package com.abl.r2mdb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@Generated
@RequiredArgsConstructor
public class MovieMetadata {
    @JsonProperty("Title")
    private final String title;

    @JsonProperty("Year")
    private final String year;

    @JsonProperty("Rated")
    private final String rated;

    @JsonProperty("Released")
    private final String released;

    @JsonProperty("Genre")
    private final String genre;

    @JsonProperty("Director")
    private final String director;

    @JsonProperty("Writer")
    private final String writer;

    @JsonProperty("Actors")
    private final String actors;

    @JsonProperty("Plot")
    private final String plot;

    @JsonProperty("Language")
    private final String language;

    @JsonProperty("Country")
    private final String country;

    @JsonProperty("Awards")
    private final String awards;

    private final String imdbRating;
    private final String imdbVotes;
    private final String imdbID;
}
