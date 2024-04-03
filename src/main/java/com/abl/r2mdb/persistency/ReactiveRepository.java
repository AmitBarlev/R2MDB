package com.abl.r2mdb.persistency;

import reactor.core.publisher.Mono;

public interface ReactiveRepository<S, T> {

    Mono<T> findById(S query);

    Mono<T> findByTitle(S query);

    void save(T movieMetadata);
}
