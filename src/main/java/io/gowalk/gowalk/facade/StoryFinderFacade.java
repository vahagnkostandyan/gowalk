package io.gowalk.gowalk.facade;

import io.gowalk.gowalk.dto.GetStoryRequest;
import io.gowalk.gowalk.dto.Story;

public interface StoryFinderFacade {
    Story findStory(GetStoryRequest getStoryRequest);
}
