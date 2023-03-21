package io.gowalk.gowalk.service;

import io.gowalk.gowalk.dto.POISearchRequest;
import io.gowalk.gowalk.dto.POISearchResult;

import java.util.List;

public interface PointOfInterestsService {
    List<POISearchResult> searchAllAvailablePOIs(POISearchRequest poiSearchRequest);

    List<POISearchResult> searchPOIs(POISearchRequest poiSearchRequest);

    POISearchResult searchNearestPOI(POISearchRequest poiSearchRequest);
}
