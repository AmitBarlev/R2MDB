package com.abl.r2mdb.persistency;

import com.abl.r2mdb.model.MovieMetadata;
import com.abl.r2mdb.model.MovieQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class OMDBClient implements ReactiveMovieDatabase {

    private final WebClient client;

    public Mono<MovieMetadata> find(MovieQuery query) {
        return client.get()
                .uri(uriBuilder -> build(uriBuilder, query))
                .retrieve()
                .bodyToMono(MovieMetadata.class);
    }

    private URI build(UriBuilder builder, MovieQuery query) {
        //probably should be extracted to another class as it's basically a conversion
        Optional.ofNullable(query.getId())
                .ifPresent(id -> builder.queryParam("i", id));
        Optional.ofNullable(query.getTitle())
                .ifPresent(title -> builder.queryParam("t", title));
        Optional.ofNullable(query.getPlot())
                .ifPresent(plot -> builder.queryParam("plot", plot.getName()));

        return builder.build();
    }
}
