package com.eatWise.mapper;

import com.eatWise.domain.Profile;
import com.eatWise.dto.request.ProfileRequest;
import com.eatWise.dto.response.ProfileResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProfileMapper1 {

    @Mapping(target = "userId", source = "user.id")
    ProfileResponse toResponse(Profile profile);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "isComplete", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Profile toEntity(ProfileRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isComplete", constant = "true")
    void updateEntityFromRequest(ProfileRequest request, @MappingTarget Profile profile);


}