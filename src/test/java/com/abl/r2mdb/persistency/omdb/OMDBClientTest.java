package com.abl.r2mdb.persistency.omdb;

import com.abl.r2mdb.model.MovieMetadata;
import com.abl.r2mdb.model.MovieQuery;
import com.abl.r2mdb.persistency.omdb.OMDBClient;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Function;

import static org.mockito.Mockito.*;

public class OMDBClientTest {

    @InjectMocks
    private OMDBClient omdbClient;

    @Mock
    private OMDBClientParameters parameters;
    @Mock
    private WebClient client;

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
    public void findById_sanity_emptyMono() {
        WebClient.RequestHeadersUriSpec<?> get = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> uri = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.ResponseSpec retrieve = mock(WebClient.ResponseSpec.class);

        doReturn(get).when(client).get();
        doReturn(uri).when(get).uri(any(Function.class));
        doReturn(retrieve).when(uri).retrieve();
        doReturn(Mono.empty()).when(retrieve).bodyToMono(MovieMetadata.class);

        Mono<MovieMetadata> metadata = omdbClient.findById(new MovieQuery());

        StepVerifier.create(metadata)
                .expectNextCount(0)
                .verifyComplete();

        verify(client).get();
        verify(get).uri(any(Function.class));
        verify(uri).retrieve();
        verify(retrieve).bodyToMono(MovieMetadata.class);
    }

    @Test
    public void findByTitle_sanity_monoWithMetadata() {
        WebClient.RequestHeadersUriSpec<?> get = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> uri = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.ResponseSpec retrieve = mock(WebClient.ResponseSpec.class);

        doReturn(get).when(client).get();
        doReturn(uri).when(get).uri(any(Function.class));
        doReturn(retrieve).when(uri).retrieve();
        doReturn(Mono.just(MovieMetadata.builder().build())).when(retrieve).bodyToMono(MovieMetadata.class);

        Mono<MovieMetadata> metadata = omdbClient.findByTitle(new MovieQuery());

        StepVerifier.create(metadata)
                .expectNextCount(1)
                .verifyComplete();

        verify(client).get();
        verify(get).uri(any(Function.class));
        verify(uri).retrieve();
        verify(retrieve).bodyToMono(MovieMetadata.class);
    }
}
