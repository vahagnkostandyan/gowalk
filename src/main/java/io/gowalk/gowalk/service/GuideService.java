package io.gowalk.gowalk.service;

import io.gowalk.gowalk.dto.GuideTalk;
import io.gowalk.gowalk.dto.GuideTopic;

public interface GuideService {
    GuideTalk tellAbout(GuideTopic topic);
}
