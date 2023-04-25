package io.gowalk.gowalk.repository;

import io.gowalk.gowalk.entity.PlaceEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface PlaceRepository extends R2dbcRepository<PlaceEntity, Long> {
    Mono<PlaceEntity> findByPlaceId(String placeId);
}
