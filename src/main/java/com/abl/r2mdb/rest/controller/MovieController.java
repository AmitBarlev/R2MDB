package com.abl.r2mdb.rest.controller;

import com.abl.r2mdb.model.MovieQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MovieController {

    @RequestMapping(value = "v1/movie", method = RequestMethod.GET)
    @ResponseBody
    public Mono<Void> movie(MovieQuery query) {
        return Mono.empty();
    }

}
