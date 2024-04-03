package com.abl.r2mdb.configuration;

import lombok.Data;

import lombok.Generated;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Generated
@Configuration
@ConfigurationProperties(prefix = "redis")
public class LocalRedisParameters {

    private String host;
    private int port;
}
