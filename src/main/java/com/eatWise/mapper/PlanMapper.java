package com.eatWise.mapper;

import com.eatWise.domain.Plan;
import com.eatWise.dto.response.PlanResponse;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PlanMapper {

    PlanResponse toPlanResponse(Plan plan);

    List<PlanResponse> toResponseList(List<Plan> plans);
}
