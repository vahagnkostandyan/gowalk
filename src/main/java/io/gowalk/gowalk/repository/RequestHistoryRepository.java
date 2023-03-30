package io.gowalk.gowalk.repository;

import io.gowalk.gowalk.entity.RequestHistoryEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface RequestHistoryRepository extends R2dbcRepository<RequestHistoryEntity, Long> {
}
