package com.abl.r2mdb.persistency;

import com.abl.r2mdb.model.MovieMetadata;
import com.abl.r2mdb.model.MovieQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ReactiveMovieRepository implements ReactiveRepositoryWithCache<MovieQuery, MovieMetadata> {

    private final ReactiveCache<String, MovieMetadata> cache;
    private final ReactiveRepository<MovieQuery, MovieMetadata> repository;

    @Override
    public Mono<MovieMetadata> findById(MovieQuery query) {
        return cache.get(query.getId())
                .switchIfEmpty(Mono.defer(
                        () -> repository.findById(query))
                        .doOnSuccess(this::save)
                );
    }

    @Override
    public Mono<MovieMetadata> findByTitle(MovieQuery query) {
        return cache.get(query.getTitle())
                .switchIfEmpty(Mono.defer(
                        () -> repository.findByTitle(query)
                                .doOnSuccess(this::save)));
    }

    @Override
    public void save(MovieMetadata metadata) {
        cache.put(metadata.getImdbID(), metadata);
        cache.put(metadata.getTitle(), metadata);
    }
}
