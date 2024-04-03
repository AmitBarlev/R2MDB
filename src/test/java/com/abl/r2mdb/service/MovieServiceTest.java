package com.abl.r2mdb.service;

import com.abl.r2mdb.model.MovieMetadata;
import com.abl.r2mdb.model.MovieQuery;
import com.abl.r2mdb.persistency.ReactiveRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class MovieServiceTest {

    @InjectMocks
    private MovieService service;

    @Mock
    private ReactiveRepository<MovieQuery, MovieMetadata> repository;

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
    public void findById_sanity_monoNotEmpty() {
        MovieQuery query = new MovieQuery();
        MovieMetadata metadata = MovieMetadata.builder().build();

        doReturn(Mono.just(metadata)).when(repository).findById(query);

        Mono<MovieMetadata> output = service.findById(query);

        StepVerifier.create(output)
                .expectNextCount(1)
                .verifyComplete();

        verify(repository).findById(query);
    }

    @Test
    public void findByTitle_sanity_monoNotEmpty() {
        MovieQuery query = new MovieQuery();
        MovieMetadata metadata = MovieMetadata.builder().build();

        doReturn(Mono.just(metadata)).when(repository).findByTitle(query);

        Mono<MovieMetadata> output = service.findByTitle(query);

        StepVerifier.create(output)
                .expectNextCount(1)
                .verifyComplete();

        verify(repository).findByTitle(query);
    }
}
