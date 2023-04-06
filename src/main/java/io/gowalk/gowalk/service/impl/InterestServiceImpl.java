package io.gowalk.gowalk.service.impl;

import io.gowalk.gowalk.entity.UsersInterestsJoinEntity;
import io.gowalk.gowalk.mapper.InterestMapper;
import io.gowalk.gowalk.model.Interest;
import io.gowalk.gowalk.repository.InterestRepository;
import io.gowalk.gowalk.repository.UsersInterestsJoinRepository;
import io.gowalk.gowalk.service.InterestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class InterestServiceImpl implements InterestService {
    private final InterestRepository interestRepository;
    private final UsersInterestsJoinRepository usersInterestsJoinRepository;
    private final InterestMapper interestMapper;

    @Override
    public Flux<Interest> saveInterests(Long userId, List<Interest> interests) {
        return interestRepository.saveAll(interestMapper.toInterestEntities(interests))
                .flatMap(interestEntity -> {
                    final UsersInterestsJoinEntity usersInterestsJoinEntity = new UsersInterestsJoinEntity();
                    usersInterestsJoinEntity.setUserId(userId);
                    usersInterestsJoinEntity.setInterestId(interestEntity.getId());
                    return usersInterestsJoinRepository.save(usersInterestsJoinEntity)
                            .thenReturn(interestEntity);
                })
                .map(interestMapper::fromInterestEntity);
    }

    @Override
    public Flux<Interest> getInterests(Long userId) {
        return usersInterestsJoinRepository.findByUserId(userId)
                .flatMap(usersInterestsJoinEntity -> interestRepository.findById(usersInterestsJoinEntity.getInterestId()))
                .map(interestMapper::fromInterestEntity);
    }
}
