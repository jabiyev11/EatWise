package com.eatWise.mapper;

import com.eatWise.client.model.request.RagRequest;
import com.eatWise.domain.Profile;
import com.eatWise.dto.request.DaysRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GeneratedPlanMapper {

    @Mapping(target = "activity", source = "profile.activityLevel")
    @Mapping(target = "dietType", source = "profile.dietaryPreference")
    @Mapping(target = "days", source = "daysRequest.days")
    RagRequest toRagRequest(Profile profile, DaysRequest daysRequest);

}
