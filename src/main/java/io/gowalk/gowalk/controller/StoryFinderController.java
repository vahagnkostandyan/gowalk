package io.gowalk.gowalk.controller;

import io.gowalk.gowalk.dto.GetStoryRequest;
import io.gowalk.gowalk.dto.Story;
import io.gowalk.gowalk.facade.StoryFinderFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/stories")
@RequiredArgsConstructor
public class StoryFinderController {
    private final StoryFinderFacade storyFinderFacade;

    @PostMapping
    public Mono<Story> getAnyStory(@RequestBody @Valid GetStoryRequest getStoryRequest) {
        return Mono.just(storyFinderFacade.findStory(getStoryRequest));
    }
}
