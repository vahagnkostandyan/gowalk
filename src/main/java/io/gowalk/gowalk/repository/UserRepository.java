package io.gowalk.gowalk.repository;

import io.gowalk.gowalk.entity.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<UserEntity, Long> {
    Mono<UserEntity> findByLocalId(String localId);

    Mono<UserEntity> findByEmail(String email);
}
