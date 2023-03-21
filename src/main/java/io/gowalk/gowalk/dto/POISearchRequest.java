package io.gowalk.gowalk.dto;

public record POISearchRequest(String interest, Location location, int radius) {
    public record Location (double latitude, double longitude){

    }
}
