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
    public List<POISearchResult> searchAllAvailablePOIs(POISearchRequest poiSearchRequest) {
        final List<PlacesSearchResponse> placesSearchResponses = new ArrayList<>();
        PlacesSearchResponse placesSearchResponse = searchPlaces(poiSearchRequest, null);
        placesSearchResponses.add(placesSearchResponse);

        while (Objects.nonNull(placesSearchResponse.nextPageToken)) {
            placesSearchResponse = searchPlaces(poiSearchRequest, placesSearchResponse.nextPageToken);
            placesSearchResponses.add(placesSearchResponse);
        }

        return placesSearchResponses.stream()
                .flatMap(places -> Arrays.stream(places.results))
                .map(result -> new POISearchResult(result.name, List.of(result.types), result.rating))
                .collect(Collectors.toList());
    }

    @Override
    public List<POISearchResult> searchPOIs(POISearchRequest poiSearchRequest) {
        PlacesSearchResponse placesSearchResponse = searchPlaces(poiSearchRequest, null);
        return Arrays.stream(placesSearchResponse.results)
                .map(result -> new POISearchResult(result.name, List.of(result.types), result.rating))
                .collect(Collectors.toList());
    }

    @Override
    public POISearchResult searchNearestPOI(POISearchRequest poiSearchRequest) {
        PlacesSearchResponse placesSearchResponse = searchPlaces(poiSearchRequest, null);
        if (placesSearchResponse.results.length == 0) {
            return null;
        }
        PlacesSearchResult result = placesSearchResponse.results[0];
        return new POISearchResult(result.name, List.of(result.types), result.rating);
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
