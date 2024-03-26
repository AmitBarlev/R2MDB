package com.abl.r2mdb.rest.controller;

import com.abl.r2mdb.model.MovieQuery;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class MovieController {

    @RequestMapping(value = "v1/movie", method = RequestMethod.GET)
    @ResponseBody
    public Mono<Void> movie() {
        return null;
    }

}
