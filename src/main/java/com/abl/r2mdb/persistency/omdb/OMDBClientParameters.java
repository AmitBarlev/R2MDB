package com.abl.r2mdb.persistency.omdb;

import lombok.Data;
import lombok.Generated;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Generated
@Configuration
@ConfigurationProperties(prefix = "omdb.client")
public class OMDBClientParameters {

    private String apiKey;
}
