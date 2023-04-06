package io.gowalk.gowalk.service;

import io.gowalk.gowalk.dto.POISearchRequest;
import io.gowalk.gowalk.dto.POISearchResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PointOfInterestsService {
    Flux<POISearchResult> searchAllAvailablePOIs(POISearchRequest poiSearchRequest);

    Flux<POISearchResult> searchPOIs(POISearchRequest poiSearchRequest);

    Mono<POISearchResult> searchNearestPOI(POISearchRequest poiSearchRequest);
}
