package com.abl.r2mdb.persistency;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

public interface ReactiveRepositoryWithCache<S, T> {

    Mono<T> findById(S query);

    Mono<T> findByTitle(S query);

    void save(T metadata);
}
