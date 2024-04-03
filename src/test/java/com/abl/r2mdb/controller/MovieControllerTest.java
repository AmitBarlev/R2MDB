package com.abl.r2mdb.controller;

import com.abl.r2mdb.model.MovieMetadata;
import com.abl.r2mdb.model.MovieQuery;
import com.abl.r2mdb.service.MovieService;
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

public class MovieControllerTest {

    @InjectMocks
    private MovieController controller;

    @Mock
    private MovieService service;

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

        doReturn(Mono.just(metadata)).when(service).findById(query);

        Mono<MovieMetadata> output = controller.findById(query);

        StepVerifier.create(output)
                .expectNextCount(1)
                .verifyComplete();

        verify(service).findById(query);
    }

    @Test
    public void findByTitle_sanity_monoNotEmpty() {
        MovieQuery query = new MovieQuery();
        MovieMetadata metadata = MovieMetadata.builder().build();

        doReturn(Mono.just(metadata)).when(service).findByTitle(query);

        Mono<MovieMetadata> output = controller.findByTitle(query);

        StepVerifier.create(output)
                .expectNextCount(1)
                .verifyComplete();

        verify(service).findByTitle(query);
    }
}
