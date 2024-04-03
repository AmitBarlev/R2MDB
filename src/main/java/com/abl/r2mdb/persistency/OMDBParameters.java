package com.abl.r2mdb.persistency;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "omdb")
public class OMDBParameters {

    private String apiKey;
    private String baseUrl;
    private int responseTimeout;
    private int channelTimeout;
    private int readTimeout;
    private int writeTimeout;
}
