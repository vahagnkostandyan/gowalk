package io.gowalk.gowalk.repository;

import io.gowalk.gowalk.entity.UsersInterestsJoinEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UsersInterestsJoinRepository extends R2dbcRepository<UsersInterestsJoinEntity, Long> {
}
