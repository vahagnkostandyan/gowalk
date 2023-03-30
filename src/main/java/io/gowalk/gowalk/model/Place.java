package io.gowalk.gowalk.model;

import lombok.Data;

import java.util.List;

@Data
public class Place {
    private Long id;
    private String name;
    private List<String> types;
    private Float rating;
    private String placeId;
}
