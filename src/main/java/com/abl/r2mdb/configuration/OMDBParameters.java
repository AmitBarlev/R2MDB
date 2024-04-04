package com.abl.r2mdb.configuration;

import lombok.Data;
import lombok.Generated;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Generated
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
