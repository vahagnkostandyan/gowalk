package io.gowalk.gowalk.dto;

import lombok.Getter;

public enum Mode {
    WALKING(100),
    DRIVING(500);

    @Getter
    private final int radius;

    Mode(int radius) {
        this.radius = radius;
    }
}
