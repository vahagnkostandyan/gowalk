package io.gowalk.gowalk.service.impl;

import io.gowalk.gowalk.exception.GetUserException;
import io.gowalk.gowalk.exception.GoWalkException;
import io.gowalk.gowalk.exception.UserCreatingException;
import io.gowalk.gowalk.mapper.UserMapper;
import io.gowalk.gowalk.model.User;
import io.gowalk.gowalk.repository.UserRepository;
import io.gowalk.gowalk.service.InterestService;
import io.gowalk.gowalk.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final InterestService interestService;

    @Override
    public Mono<User> getUserById(Long id) {
        return userRepository.findById(id)
                .flatMap(userEntity -> interestService.getInterests(userEntity.getId())
                        .collectList()
                        .map(interests -> userMapper.fromUserEntity(userEntity, interests)))
                .doOnError(ex -> log.error(ex.getMessage(), ex))
                .onErrorMap(ex -> !(ex instanceof GoWalkException), GetUserException::new);
    }

    @Override
    public Mono<User> getUserByLocalId(String localId) {
        return userRepository.findByLocalId(localId)
                .flatMap(userEntity -> interestService.getInterests(userEntity.getId())
                        .collectList()
                        .map(interests -> userMapper.fromUserEntity(userEntity, interests)))
                .doOnError(ex -> log.error(ex.getMessage(), ex))
                .onErrorMap(ex -> !(ex instanceof GoWalkException), GetUserException::new);
    }

    @Override
    public Mono<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .flatMap(userEntity -> interestService.getInterests(userEntity.getId())
                        .collectList()
                        .map(interests -> userMapper.fromUserEntity(userEntity, interests)))
                .doOnError(ex -> log.error(ex.getMessage(), ex))
                .onErrorMap(ex -> !(ex instanceof GoWalkException), GetUserException::new);
    }

    @Override
    public Mono<User> save(User user) {
        return userRepository.save(userMapper.toUserEntity(user))
                .flatMap(userEntity -> {
                    user.setId(userEntity.getId());
                    return interestService.saveInterests(userEntity.getId(), user.getInterests())
                            .collectList()
                            .map(interests -> {
                                user.setInterests(interests);
                                return user;
                            });
                })
                .doOnError(ex -> log.error(ex.getMessage(), ex))
                .switchIfEmpty(Mono.error(UserCreatingException::new))
                .onErrorMap(ex -> !(ex instanceof GoWalkException), UserCreatingException::new);
    }
}
