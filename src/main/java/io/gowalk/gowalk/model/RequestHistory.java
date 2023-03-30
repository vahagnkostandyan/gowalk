package io.gowalk.gowalk.model;

import io.gowalk.gowalk.enumerations.Mode;
import lombok.Data;

@Data
public class RequestHistory {
    private Long id;
    private User user;
    private Location location;
    private Mode mode;
    private Place place;

}
