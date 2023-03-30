package io.gowalk.gowalk.dto;

import java.util.List;

public record POISearchResult(String name, List<String> types, Float rating, String placeId) {

}
