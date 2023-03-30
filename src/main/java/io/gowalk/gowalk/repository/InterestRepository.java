package io.gowalk.gowalk.repository;

import io.gowalk.gowalk.entity.InterestEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface InterestRepository extends R2dbcRepository<InterestEntity, Long> {
}
