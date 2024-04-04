package com.abl.r2mdb.persistency;

import com.abl.r2mdb.model.MovieMetadata;
import com.abl.r2mdb.model.MovieQuery;
import com.abl.r2mdb.model.Plot;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class ReactiveMovieRepositoryTest {

    @InjectMocks
    private ReactiveMovieRepository repositoryWithCache;

    @Mock
    private ReactiveCache<String, MovieMetadata> cache;

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
    public void save_sanity_methodsCalled() {
        MovieMetadata metadata = MovieMetadata.builder()
                .imdbID("1")
                .title("2")
                .build();

        repositoryWithCache.save(metadata);

        verify(cache).put(metadata.getImdbID(), metadata);
        verify(cache).put(metadata.getTitle(), metadata);
    }

    @Test
    public void findById_existsInCache_monoWithData() {
        MovieQuery query = new MovieQuery("id", "title", "year", Plot.SHORT, "type");
        MovieMetadata metadata = MovieMetadata.builder().build();

        doReturn(Mono.just(metadata)).when(cache).get(query.getId());

        Mono<MovieMetadata> output = repositoryWithCache.findById(query);

        StepVerifier.create(output)
                .expectNextCount(1)
                .verifyComplete();

        verify(cache).get(query.getId());
    }

    @Test
    public void findById_doesntExistInCache_monoWithData() {
        MovieQuery query = new MovieQuery("id", "title", "year", Plot.SHORT, "type");
        MovieMetadata metadata = MovieMetadata.builder().build();

        doReturn(Mono.empty()).when(cache).get(query.getId());
        doReturn(Mono.just(metadata)).when(repository).findById(query);

        Mono<MovieMetadata> output = repositoryWithCache.findById(query);

        StepVerifier.create(output)
                .expectNextCount(1)
                .verifyComplete();

        verify(cache).get(query.getId());
        verify(repository).findById(query);
    }

    @Test
    public void findByTitle_existsInCache_monoWithData() {
        MovieQuery query = new MovieQuery("id", "title", "year", Plot.SHORT, "type");
        MovieMetadata metadata = MovieMetadata.builder().build();

        doReturn(Mono.just(metadata)).when(cache).get(query.getTitle());

        Mono<MovieMetadata> output = repositoryWithCache.findByTitle(query);

        StepVerifier.create(output)
                .expectNextCount(1)
                .verifyComplete();

        verify(cache).get(query.getTitle());
    }

    @Test
    public void findByTitle_doesntExistInCache_monoWithData() {
        MovieQuery query = new MovieQuery("id", "title", "year", Plot.SHORT, "type");
        MovieMetadata metadata = MovieMetadata.builder().build();

        doReturn(Mono.empty()).when(cache).get(query.getTitle());
        doReturn(Mono.just(metadata)).when(repository).findByTitle(query);

        Mono<MovieMetadata> output = repositoryWithCache.findByTitle(query);

        StepVerifier.create(output)
                .expectNextCount(1)
                .verifyComplete();

        verify(cache).get(query.getTitle());
        verify(repository).findByTitle(query);
    }
}
