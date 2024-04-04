package com.abl.r2mdb.persistency.omdb;

import com.abl.r2mdb.model.MovieMetadata;
import com.abl.r2mdb.model.MovieQuery;
import com.abl.r2mdb.persistency.ReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class OMDBClient implements ReactiveRepository<MovieQuery, MovieMetadata> {

    private final OMDBClientParameters parameters;
    private final WebClient client;

    @Override
    public Mono<MovieMetadata> findById(MovieQuery query) {
        return client.get()
                .uri(builder -> builder
                        .queryParam("apiKey", parameters.getApiKey())
                        .queryParam("i", query.getId())
                        .build())
                .retrieve()
                .bodyToMono(MovieMetadata.class);
    }

    @Override
    public Mono<MovieMetadata> findByTitle(MovieQuery query) {
        return client.get()
                .uri(builder -> builder
                        .queryParam("apiKey", parameters.getApiKey())
                        .queryParam("t", query.getTitle())
                        .build())
                .retrieve()
                .bodyToMono(MovieMetadata.class);
    }
}
