package com.abl.r2mdb.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
@RequiredArgsConstructor
public class LocalRedisConfiguration {

    private final LocalRedisParameters parameters;

    @Bean
    public RedissonReactiveClient redisClient() {
        String host = parameters.getHost();
        int port = parameters.getPort();

        if (StringUtils.isBlank(host) || 0 == port)
            throw new IllegalArgumentException("Redis parameters are invalid");

        String connection = String.format("redis://%s:%s", host, port);
        Config config = new Config();
        config.useSingleServer()
                .setAddress(connection);

        RedissonClient client = Redisson.create(config);
        return client.reactive();
    }
}
