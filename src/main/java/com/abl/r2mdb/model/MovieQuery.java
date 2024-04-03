package com.abl.r2mdb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieQuery {

    private String id;
    private String title;
    private String year;
    private Plot plot;
    private String type;
}
