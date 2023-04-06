package io.gowalk.gowalk.facade.impl;

import io.gowalk.gowalk.dto.GetStoryRequest;
import io.gowalk.gowalk.dto.GuideTopic;
import io.gowalk.gowalk.dto.POISearchRequest;
import io.gowalk.gowalk.dto.Story;
import io.gowalk.gowalk.facade.StoryFinderFacade;
import io.gowalk.gowalk.model.Interest;
import io.gowalk.gowalk.model.User;
import io.gowalk.gowalk.service.GuideService;
import io.gowalk.gowalk.service.PointOfInterestsService;
import io.gowalk.gowalk.service.UserService;
import io.gowalk.gowalk.util.GeneralUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

import static io.gowalk.gowalk.util.Constants.USER_ID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimpleStoryFinderFacadeImpl implements StoryFinderFacade {
    private final PointOfInterestsService pointOfInterestsService;
    private final GuideService guideService;
    private final UserService userService;

    @Override
    public Mono<Story> findStory(GetStoryRequest getStoryRequest) {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> (JwtAuthenticationToken) securityContext.getAuthentication())
                .flatMap(authentication -> userService.getUserByLocalId(
                        (String) authentication.getTokenAttributes().get(USER_ID)))
                .flatMap(user -> pointOfInterestsService.searchNearestPOI(new POISearchRequest(GeneralUtils.randomlyGetInterest(user),
                        new POISearchRequest.Location(getStoryRequest.getLatitude(), getStoryRequest.getLongitude()),
                        getStoryRequest.getMode().getRadius())))
                .flatMap(poiSearchResult -> guideService.tellAbout(new GuideTopic(poiSearchResult.name(), List.of())))
                .map(guideTalk -> new Story(guideTalk.name(), guideTalk.content()));
    }
}
