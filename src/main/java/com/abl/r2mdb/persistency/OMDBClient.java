package com.abl.r2mdb.persistency;

import com.abl.r2mdb.model.MovieMetadata;
import com.abl.r2mdb.model.MovieQuery;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Component
@RequiredArgsConstructor
public class OMDBClient implements ReactiveMovieDatabase {

    private final OMDBParameters parameters;
    private WebClient client;

    @PostConstruct
    public void init() {
        HttpClient http = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, parameters.getChannelTimeout())
                .responseTimeout(Duration.ofMillis(parameters.getResponseTimeout()))
                .doOnConnected(connection ->
                        connection
                                .addHandlerLast(new ReadTimeoutHandler(parameters.getReadTimeout(), TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(parameters.getWriteTimeout(), TimeUnit.MILLISECONDS))
                );

        client = WebClient.builder()
                .baseUrl(parameters.getBaseUrl())
                .clientConnector(new ReactorClientHttpConnector(http))
                .build();
    }

    public Mono<MovieMetadata> find(MovieQuery query) {
        return client.get()
                .uri(uriBuilder -> build(uriBuilder, query))
                .retrieve()
                .bodyToMono(MovieMetadata.class);
    }

    private URI build(UriBuilder builder, MovieQuery query) {
        UriBuilder fBuilder = builder
                .queryParam("apikey", parameters.getApiKey());

        Optional.ofNullable(query.getId())
                .ifPresent(id -> fBuilder.queryParam("i", id));
        Optional.ofNullable(query.getTitle())
                .ifPresent(title -> fBuilder.queryParam("t", title));
        Optional.ofNullable(query.getPlot())
                .ifPresent(plot -> fBuilder.queryParam("plot", plot.getName()));

        return fBuilder.build();
    }
}
