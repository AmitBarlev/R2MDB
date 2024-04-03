package com.abl.r2mdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = RedisReactiveAutoConfiguration.class)
public class R2mdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(R2mdbApplication.class, args);
	}

}
