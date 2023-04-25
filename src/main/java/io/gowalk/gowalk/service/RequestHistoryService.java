package io.gowalk.gowalk.service;

import io.gowalk.gowalk.model.RequestHistory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RequestHistoryService {
    Mono<Void> addUserRequest(long userId, RequestHistory requestHistory);

    Flux<RequestHistory> getUserRequests(long userId);
}
