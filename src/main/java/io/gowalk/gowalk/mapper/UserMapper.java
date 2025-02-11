package io.gowalk.gowalk.mapper;

import io.gowalk.gowalk.entity.UserEntity;
import io.gowalk.gowalk.model.Interest;
import io.gowalk.gowalk.model.User;
import org.mapstruct.Mapper;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;
import java.util.Map;

import static io.gowalk.gowalk.util.Constants.EMAIL;
import static io.gowalk.gowalk.util.Constants.USER_ID;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromUserEntity(UserEntity userEntity, List<Interest> interests);

    UserEntity toUserEntity(User user);

    default User fromAuthenticationAndInterests(JwtAuthenticationToken authentication, List<Interest> interests) {
        final Map<String, Object> tokenAttributes = authentication.getTokenAttributes();
        final User user = new User();
        user.setEmail((String) tokenAttributes.get(EMAIL));
        user.setLocalId((String) tokenAttributes.get(USER_ID));
        user.setInterests(interests);
        return user;
    }
}
