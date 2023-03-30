package io.gowalk.gowalk.repository;

import io.gowalk.gowalk.entity.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserRepository extends R2dbcRepository<UserEntity, Long> {
}
