package io.gowalk.gowalk.service;

import io.gowalk.gowalk.model.Interest;
import reactor.core.publisher.Flux;

import java.util.List;

public interface InterestService {
    Flux<Interest> saveInterests(Long userId, List<Interest> interests);

    Flux<Interest> getInterests(Long userId);
}
