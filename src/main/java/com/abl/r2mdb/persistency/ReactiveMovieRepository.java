package com.abl.r2mdb.persistency;

import com.abl.r2mdb.model.MovieMetadata;
import com.abl.r2mdb.model.MovieQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ReactiveMovieRepository implements ReactiveRepository<MovieQuery, MovieMetadata> {

    private final ReactiveCache<String, MovieMetadata> cache;
    private final ReactiveMovieDatabase database;

    @Override
    public Mono<MovieMetadata> findById(MovieQuery query) {
        return cache.get(query.getId())
                .switchIfEmpty(doOnEmpty(query));
    }

    @Override
    public Mono<MovieMetadata> findByTitle(MovieQuery query) {
        return cache.get(query.getTitle())
                .switchIfEmpty(doOnEmpty(query));
    }

    @Override
    public void save(MovieMetadata metadata) {
        cache.put(metadata.getImdbID(), metadata);
        cache.put(metadata.getTitle(), metadata);
    }

    private Mono<MovieMetadata> doOnEmpty(MovieQuery query) {
        return database.find(query)
                .doOnSuccess(this::save);
    }
}
