package com.abl.r2mdb.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public record MovieQuery(String id, String title, String year, Plot plot) {

}
