package io.gowalk.gowalk.repository;

import io.gowalk.gowalk.entity.UsersInterestsJoinEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface UsersInterestsJoinRepository extends R2dbcRepository<UsersInterestsJoinEntity, Long> {
    Flux<UsersInterestsJoinEntity> findByUserId(Long userId);
}
