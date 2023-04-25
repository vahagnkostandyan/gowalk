package io.gowalk.gowalk.service.impl;

import io.gowalk.gowalk.entity.RequestHistoryEntity;
import io.gowalk.gowalk.exception.CreateRequestHistoryException;
import io.gowalk.gowalk.exception.GetRequestHistoryException;
import io.gowalk.gowalk.exception.GoWalkException;
import io.gowalk.gowalk.mapper.PlaceMapper;
import io.gowalk.gowalk.mapper.RequestHistoryMapper;
import io.gowalk.gowalk.model.Place;
import io.gowalk.gowalk.model.RequestHistory;
import io.gowalk.gowalk.repository.PlaceRepository;
import io.gowalk.gowalk.repository.RequestHistoryRepository;
import io.gowalk.gowalk.service.RequestHistoryService;
import io.gowalk.gowalk.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestHistoryServiceImpl implements RequestHistoryService {
    private final RequestHistoryRepository requestHistoryRepository;
    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;
    private final UserService userService;
    private final RequestHistoryMapper requestHistoryMapper;

    @Override
    public Mono<Void> addUserRequest(long userId, RequestHistory requestHistory) {
        Place place = requestHistory.getPlace();
        log.info("Try to add new request history. Place id: {} User id: {}", place.getPlaceId(), userId);
        final RequestHistoryEntity requestHistoryEntity = requestHistoryMapper.toEntity(requestHistory);
        requestHistoryEntity.setUserId(userId);
        return placeRepository.findByPlaceId(place.getPlaceId())
                .switchIfEmpty(Mono.defer(() -> placeRepository.save(placeMapper.toEntity(place))))
                .flatMap(entity -> {
                    requestHistoryEntity.setPlaceId(entity.getId());
                    return requestHistoryRepository.save(requestHistoryEntity);
                })
                .doOnNext(entity -> log.info("Successfully saved new request history for user: {}. Request id: {}",
                        userId, entity.getId()))
                .doOnError(ex -> log.error(ex.getMessage(), ex))
                .switchIfEmpty(Mono.error(CreateRequestHistoryException::new))
                .onErrorMap(ex -> !(ex instanceof GoWalkException), CreateRequestHistoryException::new)
                .then();
    }

    @Override
    public Flux<RequestHistory> getUserRequests(long userId) {
        return requestHistoryRepository.findAllByUserId(userId)
                .zipWith(userService.getUserById(userId))
                .flatMap(entities -> placeRepository.findById(entities.getT1().getPlaceId())
                        .map(placeMapper::fromEntity)
                        .map(place -> requestHistoryMapper.fromEntity(entities.getT1(), entities.getT2(), place)))
                .doOnError(ex -> log.error(ex.getMessage(), ex))
                .onErrorMap(ex -> !(ex instanceof GoWalkException), GetRequestHistoryException::new);
    }
}
