package io.gowalk.gowalk.mapper;

import io.gowalk.gowalk.entity.PlaceEntity;
import io.gowalk.gowalk.model.Place;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaceMapper {
    PlaceEntity toEntity(Place place);
    Place fromEntity(PlaceEntity place);
}
