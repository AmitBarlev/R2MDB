package com.abl.r2mdb.persistency;

import com.abl.r2mdb.model.MovieMetadata;
import com.abl.r2mdb.model.MovieQuery;
import reactor.core.publisher.Mono;

public interface ReactiveMovieDatabase {

    Mono<MovieMetadata> find(MovieQuery query);
}
