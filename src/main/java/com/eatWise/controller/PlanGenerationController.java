package com.eatWise.controller;

import com.eatWise.client.model.response.RagResponse;
import com.eatWise.dto.request.DaysRequest;
import com.eatWise.service.PlanGenerationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/plan")
@RequiredArgsConstructor
public class PlanGenerationController {

    private final PlanGenerationService planGenerationService;

    @PostMapping("/generate")
    public RagResponse generatePlan(@Valid @RequestBody DaysRequest daysRequest){
        return planGenerationService.generatePlan(daysRequest);
    }

}
