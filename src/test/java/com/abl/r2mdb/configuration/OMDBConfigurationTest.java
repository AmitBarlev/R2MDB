package com.abl.r2mdb.configuration;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class OMDBConfigurationTest {

    @InjectMocks
    private OMDBConfiguration configuration;

    @Mock
    private OMDBConfigurationParameters parameters;

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
    public void omdbClient_sanity_nonNullClient() {
        doReturn(5000).when(parameters).getChannelTimeout();
        doReturn(4000).when(parameters).getResponseTimeout();
        doReturn("base").when(parameters).getBaseUrl();

        WebClient client = configuration.omdbClient();
        assertNotNull(client);

        verify(parameters).getChannelTimeout();
        verify(parameters).getResponseTimeout();
        verify(parameters).getBaseUrl();
    }
}
