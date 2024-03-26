package com.abl.r2mdb.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum Plot {

    SHORT("short"),
    FULL("full");

    private final String name;
}
