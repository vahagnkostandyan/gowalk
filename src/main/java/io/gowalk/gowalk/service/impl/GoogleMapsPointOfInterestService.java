package io.gowalk.gowalk.service.impl;

import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.google.maps.model.RankBy;
import io.gowalk.gowalk.dto.POISearchRequest;
import io.gowalk.gowalk.dto.POISearchResult;
import io.gowalk.gowalk.service.PointOfInterestsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GoogleMapsPointOfInterestService implements PointOfInterestsService {

    @Value("${app.google-maps.api-key}")
    private String apiKey;

    @Override
    public Flux<POISearchResult> searchAllAvailablePOIs(POISearchRequest poiSearchRequest) {
        return Mono.fromCallable(() -> searchPlaces(poiSearchRequest, null))
                .expand(placesSearchResponse -> {
                    if (Objects.nonNull(placesSearchResponse.nextPageToken)) {
                        return Mono.fromCallable(() -> searchPlaces(poiSearchRequest, placesSearchResponse.nextPageToken));
                    }
                    return Mono.empty();
                })
                .filter(placesSearchResponse -> placesSearchResponse.results.length > 0)
                .flatMap(placesSearchResponse -> Flux.fromArray(placesSearchResponse.results))
                .map(result -> new POISearchResult(result.name, List.of(result.types), result.rating, result.placeId));
    }

    @Override
    public Flux<POISearchResult> searchPOIs(POISearchRequest poiSearchRequest) {
        return Mono.fromCallable(() -> searchPlaces(poiSearchRequest, null))
                .filter(placesSearchResponse -> placesSearchResponse.results.length > 0)
                .flatMapMany(placesSearchResponse -> Flux.fromArray(placesSearchResponse.results))
                .map(result -> new POISearchResult(result.name, List.of(result.types), result.rating, result.placeId));
    }

    @Override
    public Mono<POISearchResult> searchNearestPOI(POISearchRequest poiSearchRequest) {
        return Mono.fromCallable(() -> searchPlaces(poiSearchRequest, null))
                .filter(placesSearchResponse -> placesSearchResponse.results.length > 0)
                .map(placesSearchResponse -> placesSearchResponse.results[0])
                .map(result -> new POISearchResult(result.name, List.of(result.types), result.rating, result.placeId));
    }

    private PlacesSearchResponse searchPlaces(POISearchRequest poiSearchRequest, String pageToken) {
        POISearchRequest.Location requestLocation = poiSearchRequest.location();
        LatLng location = new LatLng(requestLocation.latitude(), requestLocation.longitude());
        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
        NearbySearchRequest nearbySearchRequest = PlacesApi.nearbySearchQuery(geoApiContext, location);
        nearbySearchRequest.keyword(poiSearchRequest.interest());
        if (Objects.nonNull(pageToken) && !pageToken.isBlank()) {
            nearbySearchRequest.pageToken(pageToken);
        }
        if (poiSearchRequest.radius() > 0) {
            nearbySearchRequest.radius(poiSearchRequest.radius());
        } else {
            nearbySearchRequest.rankby(RankBy.DISTANCE);
        }
        nearbySearchRequest.language("EN");
        return nearbySearchRequest.awaitIgnoreError();
    }
}
