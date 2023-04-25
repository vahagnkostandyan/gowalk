package io.gowalk.gowalk.mapper;

import io.gowalk.gowalk.dto.GetStoryRequest;
import io.gowalk.gowalk.dto.POISearchResult;
import io.gowalk.gowalk.entity.RequestHistoryEntity;
import io.gowalk.gowalk.model.Location;
import io.gowalk.gowalk.model.Place;
import io.gowalk.gowalk.model.RequestHistory;
import io.gowalk.gowalk.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestHistoryMapper {
    default RequestHistory fromEntity(RequestHistoryEntity entity, User user, Place place) {
        final RequestHistory requestHistory = new RequestHistory();
        requestHistory.setId(entity.getId());
        requestHistory.setMode(entity.getMode());
        requestHistory.setUser(user);
        final Location location = new Location();
        location.setLatitude(entity.getLatitude());
        location.setLongitude(entity.getLongitude());
        requestHistory.setLocation(location);
        requestHistory.setPlace(place);
        return requestHistory;
    }

    default RequestHistoryEntity toEntity(RequestHistory requestHistory) {
        final RequestHistoryEntity entity = new RequestHistoryEntity();
        entity.setUserId(requestHistory.getUser().getId());
        entity.setMode(requestHistory.getMode());
        entity.setLongitude(requestHistory.getLocation().getLongitude());
        entity.setLatitude(requestHistory.getLocation().getLatitude());
        entity.setId(requestHistory.getId());
        entity.setPlaceId(requestHistory.getPlace().getId());
        return entity;
    }

    default RequestHistory createNewRequestHistory(User user, POISearchResult poiSearchResult, GetStoryRequest getStoryRequest) {
        final RequestHistory requestHistory = new RequestHistory();
        requestHistory.setMode(getStoryRequest.getMode());
        requestHistory.setUser(user);
        final Location location = new Location();
        location.setLatitude(getStoryRequest.getLatitude());
        location.setLongitude(getStoryRequest.getLongitude());
        requestHistory.setLocation(location);
        final Place place = createPlace(poiSearchResult);
        requestHistory.setPlace(place);
        return requestHistory;
    }

    default Place createPlace(POISearchResult poiSearchResult) {
        final Place place = new Place();
        place.setPlaceId(poiSearchResult.placeId());
        place.setName(poiSearchResult.name());
        place.setTypes(poiSearchResult.types());
        place.setRating(poiSearchResult.rating());
        return place;
    }
}
