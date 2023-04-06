package io.gowalk.gowalk.service;

import io.gowalk.gowalk.model.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> getUserById(Long id);

    Mono<User> getUserByLocalId(String localId);

    Mono<User> getUserByEmail(String email);

    Mono<User> save(User user);
}
