package com.abl.r2mdb.service;

import com.abl.r2mdb.model.MovieMetadata;
import com.abl.r2mdb.model.MovieQuery;
import com.abl.r2mdb.persistency.ReactiveRepository;
import com.abl.r2mdb.persistency.ReactiveRepositoryWithCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final ReactiveRepositoryWithCache<MovieQuery, MovieMetadata> movieRepository;

    public Mono<MovieMetadata> findById(MovieQuery query) {
        return movieRepository.findById(query);
    }

    public Mono<MovieMetadata> findByTitle(MovieQuery query) {
        return movieRepository.findByTitle(query);
    }
}
