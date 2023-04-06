package io.gowalk.gowalk.controller;

import io.gowalk.gowalk.dto.CreateUserRequest;
import io.gowalk.gowalk.mapper.InterestMapper;
import io.gowalk.gowalk.mapper.UserMapper;
import io.gowalk.gowalk.model.User;
import io.gowalk.gowalk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class InterestsController {
    private final UserService userService;
    private final InterestMapper interestMapper;
    private final UserMapper userMapper;

    @PostMapping
    public Mono<User> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return ReactiveSecurityContextHolder.getContext()
                .flatMap(context ->
                        userService.save(
                                userMapper.fromAuthenticationAndInterests(
                                        (JwtAuthenticationToken) context.getAuthentication(),
                                        interestMapper.fromCreateUserRequest(createUserRequest))));
    }
}
