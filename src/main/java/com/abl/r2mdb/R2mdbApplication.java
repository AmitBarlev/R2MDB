package com.abl.r2mdb;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;

@Generated
@SpringBootApplication
public class R2mdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(R2mdbApplication.class, args);
	}

}
