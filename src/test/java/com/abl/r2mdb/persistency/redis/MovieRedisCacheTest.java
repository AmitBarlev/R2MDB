package com.abl.r2mdb.persistency.redis;

import com.abl.r2mdb.model.MovieMetadata;
import com.abl.r2mdb.persistency.redis.MovieRedisCache;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.redisson.api.RBucketReactive;
import org.redisson.api.RedissonReactiveClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

public class MovieRedisCacheTest {

    @InjectMocks
    private MovieRedisCache cache;

    @Mock
    private RedissonReactiveClient client;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    @SneakyThrows
    void tearDown() {
        closeable.close();
    }

    @Test
    public void get_sanity_emptyMono() {
        final String key = "key";
        RBucketReactive<MovieMetadata> bucket = mock(RBucketReactive.class);

        doReturn(bucket).when(client).getBucket(key);
        doReturn(Mono.empty()).when(bucket).get();

        Mono<MovieMetadata> output = cache.get(key);

        StepVerifier.create(output)
                .expectNextCount(0)
                .verifyComplete();

        verify(client).getBucket(key);
    }

    @Test
    public void get_sanity_monoWithMetadata() {
        final String key = "key";
        RBucketReactive<MovieMetadata> bucket = mock(RBucketReactive.class);

        doReturn(bucket).when(client).getBucket(key);
        doReturn(Mono.just(MovieMetadata.builder().build())).when(bucket).get();

        Mono<MovieMetadata> output = cache.get(key);

        StepVerifier.create(output)
                .expectNextCount(1)
                .verifyComplete();

        verify(client).getBucket(key);
    }

    @Test
    public void save_sanity_monoWithMetadata() {
        final String key = "key";
        MovieMetadata metadata = MovieMetadata.builder().build();
        RBucketReactive<MovieMetadata> bucket = mock(RBucketReactive.class);

        doReturn(bucket).when(client).getBucket(key);
        doReturn(Mono.empty()).when(bucket).set(metadata);

        cache.put(key, metadata);

        verify(client).getBucket(key);
    }
}
