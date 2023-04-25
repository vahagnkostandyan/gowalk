package io.gowalk.gowalk.repository;

import io.gowalk.gowalk.entity.RequestHistoryEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RequestHistoryRepository extends R2dbcRepository<RequestHistoryEntity, Long> {
    Mono<RequestHistoryEntity> findByPlaceId(Long placeId);

    Flux<RequestHistoryEntity> findAllByUserId(Long userId);
}
