package com.abl.r2mdb.rest.controller;

import com.abl.r2mdb.model.MovieMetadata;
import com.abl.r2mdb.model.MovieQuery;
import com.abl.r2mdb.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieService service;

    @GetMapping(value = "v1/movie/id")
    public Mono<MovieMetadata> findById(MovieQuery query) {
        return service.findById(query);
    }

    @GetMapping(value = "v1/movie/title")
    public Mono<MovieMetadata> findByTitle(MovieQuery query) {
        return service.findByTitle(query);
    }
}
