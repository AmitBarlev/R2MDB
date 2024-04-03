package com.abl.r2mdb.persistency;

import reactor.core.publisher.Mono;

public interface ReactiveCache<K, V> {
    Mono<V> get(K key);
    void put(K key, V value);
}
