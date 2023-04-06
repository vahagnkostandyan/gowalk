package io.gowalk.gowalk.service;

import io.gowalk.gowalk.dto.GuideTalk;
import io.gowalk.gowalk.dto.GuideTopic;
import reactor.core.publisher.Mono;

public interface GuideService {
    Mono<GuideTalk> tellAbout(GuideTopic topic);
}
