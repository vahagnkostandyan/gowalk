package io.gowalk.gowalk.mapper;

import io.gowalk.gowalk.dto.CreateUserRequest;
import io.gowalk.gowalk.entity.InterestEntity;
import io.gowalk.gowalk.model.Interest;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface InterestMapper {
    Interest fromInterestEntity(InterestEntity interestEntity);

    InterestEntity toInterestEntity(Interest interest);
    List<InterestEntity> toInterestEntities(List<Interest> interest);

    default List<Interest> fromCreateUserRequest(CreateUserRequest interestsRequest) {
        if (Objects.isNull(interestsRequest)) {
            return null;
        }
        return interestsRequest.getInterests().stream()
                .map(this::createInterest)
                .collect(Collectors.toList());
    }

    default Interest createInterest(String interestStr) {
        Interest interest = new Interest();
        interest.setInterest(interestStr);
        return interest;
    }
}
