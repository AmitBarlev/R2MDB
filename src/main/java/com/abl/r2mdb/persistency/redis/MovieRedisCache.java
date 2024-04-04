package com.abl.r2mdb.persistency.redis;

import com.abl.r2mdb.model.MovieMetadata;
import com.abl.r2mdb.persistency.ReactiveCache;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MovieRedisCache implements ReactiveCache<String, MovieMetadata> {

    private final RedissonReactiveClient client;

    @Override
    public Mono<MovieMetadata> get(String key) {
        return client.<MovieMetadata>getBucket(key)
                .get();
    }

    @Override
    public void put(String key, MovieMetadata value) {
        client.<MovieMetadata>getBucket(key)
                .set(value)
                .subscribe();
    }
}
