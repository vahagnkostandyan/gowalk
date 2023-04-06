package io.gowalk.gowalk.facade;

import io.gowalk.gowalk.dto.GetStoryRequest;
import io.gowalk.gowalk.dto.Story;
import reactor.core.publisher.Mono;

public interface StoryFinderFacade {
    Mono<Story> findStory(GetStoryRequest getStoryRequest);
}
