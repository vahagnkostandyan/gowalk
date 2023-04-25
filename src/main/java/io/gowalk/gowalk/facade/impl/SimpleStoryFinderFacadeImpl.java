package io.gowalk.gowalk.facade.impl;

import io.gowalk.gowalk.dto.GetStoryRequest;
import io.gowalk.gowalk.dto.GuideTopic;
import io.gowalk.gowalk.dto.POISearchRequest;
import io.gowalk.gowalk.dto.Story;
import io.gowalk.gowalk.exception.FindStoryException;
import io.gowalk.gowalk.exception.GoWalkException;
import io.gowalk.gowalk.facade.StoryFinderFacade;
import io.gowalk.gowalk.mapper.RequestHistoryMapper;
import io.gowalk.gowalk.model.Interest;
import io.gowalk.gowalk.model.User;
import io.gowalk.gowalk.service.GuideService;
import io.gowalk.gowalk.service.PointOfInterestsService;
import io.gowalk.gowalk.service.RequestHistoryService;
import io.gowalk.gowalk.service.UserService;
import io.gowalk.gowalk.util.GeneralUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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
    private final RequestHistoryService requestHistoryService;
    private final RequestHistoryMapper requestHistoryMapper;

    @Override
    public Mono<Story> findStory(GetStoryRequest getStoryRequest) {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> (JwtAuthenticationToken) securityContext.getAuthentication())
                .flatMap(authentication -> userService.getUserByLocalId(
                        (String) authentication.getTokenAttributes().get(USER_ID)))
                .flatMap(user -> requestHistoryService.getUserRequests(user.getId())
                        .map(requestHistory -> requestHistory.getPlace().getPlaceId())
                        .collectList()
                        .flatMap(placeIds -> pointOfInterestsService.searchPOIs(
                                        new POISearchRequest(GeneralUtils.randomlyGetInterest(user),
                                                new POISearchRequest.Location(getStoryRequest.getLatitude(), getStoryRequest.getLongitude()),
                                                getStoryRequest.getMode().getRadius()))
                                .filter(poiSearchResults -> !placeIds.contains(poiSearchResults.placeId()))
                                .next()
                                .switchIfEmpty(Mono.defer(() -> pointOfInterestsService.searchAllAvailablePOIs(
                                                new POISearchRequest(GeneralUtils.randomlyGetInterest(user),
                                                        new POISearchRequest.Location(getStoryRequest.getLatitude(), getStoryRequest.getLongitude()),
                                                        getStoryRequest.getMode().getRadius()))
                                        .filter(poiSearchResults -> !placeIds.contains(poiSearchResults.placeId()))
                                        .next())))
                        .switchIfEmpty(Mono.defer(() -> pointOfInterestsService.searchNearestPOI(new POISearchRequest(GeneralUtils.randomlyGetInterest(user),
                                new POISearchRequest.Location(getStoryRequest.getLatitude(), getStoryRequest.getLongitude()),
                                getStoryRequest.getMode().getRadius()))))
                        .flatMap(poiSearchResult -> guideService.tellAbout(new GuideTopic(poiSearchResult.name(), List.of()))
                                .publishOn(Schedulers.boundedElastic())
                                .doOnSuccess(guideTalk -> requestHistoryService.addUserRequest(
                                                user.getId(),
                                                requestHistoryMapper.createNewRequestHistory(
                                                        user,
                                                        poiSearchResult,
                                                        getStoryRequest))
                                        .subscribe()))
                        .map(guideTalk -> new Story(guideTalk.name(), guideTalk.content())))
                .switchIfEmpty(Mono.error(FindStoryException::new))
                .doOnError(e -> log.error(e.getMessage(), e))
                .onErrorMap(ex -> !(ex instanceof GoWalkException), FindStoryException::new);
    }
}
