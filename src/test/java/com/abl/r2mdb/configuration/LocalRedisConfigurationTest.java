package com.abl.r2mdb.configuration;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class LocalRedisConfigurationTest {

    @InjectMocks
    private LocalRedisConfiguration redisConfiguration;

    @Mock
    private LocalRedisParameters parameters;

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
    public void redisClient_nullHost_exceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> redisConfiguration.redisClient());

        verify(parameters).getHost();
        verify(parameters).getPort();
    }

    @Test
    public void redisClient_portIsZero_exceptionThrown() {
        doReturn("_").when(parameters).getHost();

        assertThrows(IllegalArgumentException.class, () -> redisConfiguration.redisClient());

        verify(parameters).getHost();
        verify(parameters).getPort();
    }

    @Test
    public void redisClient_sanity_nonNullRedissonClient() {
        RedissonClient client = mock(RedissonClient.class);
        RedissonReactiveClient reactive = mock(RedissonReactiveClient.class);

        doReturn("127.0.0.1").when(parameters).getHost();
        doReturn(6379).when(parameters).getPort();
        doReturn(reactive).when(client).reactive();

        try (MockedStatic<Redisson> redisson = Mockito.mockStatic(Redisson.class)) {
            redisson.when(() -> Redisson.create(any())).thenReturn(client);

            RedissonReactiveClient output = redisConfiguration.redisClient();

            assertNotNull(output);
            redisson.verify(() -> Redisson.create(any()));
        }

        verify(parameters).getPort();
        verify(parameters).getHost();
    }
}
