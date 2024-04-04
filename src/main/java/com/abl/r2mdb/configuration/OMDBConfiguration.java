package com.abl.r2mdb.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class OMDBConfiguration {

    private final OMDBConfigurationParameters parameters;

    @Bean
    public WebClient omdbClient() {
        HttpClient http = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, parameters.getChannelTimeout())
                .responseTimeout(Duration.ofMillis(parameters.getResponseTimeout()))
                .doOnConnected(connection ->
                        connection
                                .addHandlerLast(new ReadTimeoutHandler(parameters.getReadTimeout(), TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(parameters.getWriteTimeout(), TimeUnit.MILLISECONDS))
                );

        return WebClient.builder()
                .baseUrl(parameters.getBaseUrl())
                .clientConnector(new ReactorClientHttpConnector(http))
                .build();
    }
}
