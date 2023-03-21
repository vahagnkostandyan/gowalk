package io.gowalk.gowalk.facade.impl;

import io.gowalk.gowalk.dto.*;
import io.gowalk.gowalk.facade.StoryFinderFacade;
import io.gowalk.gowalk.service.GuideService;
import io.gowalk.gowalk.service.PointOfInterestsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimpleStoryFinderFacadeImpl implements StoryFinderFacade {
    private final PointOfInterestsService pointOfInterestsService;
    private final GuideService guideService;

    @Override
    public Story findStory(GetStoryRequest getStoryRequest) {
        POISearchResult poiSearchResult = pointOfInterestsService.searchNearestPOI(new POISearchRequest("museum",
                new POISearchRequest.Location(getStoryRequest.getLatitude(), getStoryRequest.getLongitude()),
                getStoryRequest.getMode().getRadius()));
        GuideTalk guideTalk = guideService.tellAbout(new GuideTopic(poiSearchResult.name(), List.of()));
        return new Story(guideTalk.name(), guideTalk.content());
    }
}
