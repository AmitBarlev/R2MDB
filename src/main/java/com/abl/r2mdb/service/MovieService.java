package com.abl.r2mdb.service;

import com.abl.r2mdb.model.MovieMetadata;
import com.abl.r2mdb.model.MovieQuery;
import com.abl.r2mdb.persistency.ReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final ReactiveRepository<MovieQuery, MovieMetadata> repository;

    public Mono<MovieMetadata> findById(MovieQuery query) {
        return repository.findById(query);
    }

    public Mono<MovieMetadata> findByTitle(MovieQuery query) {
        return repository.findByTitle(query);
    }
}
