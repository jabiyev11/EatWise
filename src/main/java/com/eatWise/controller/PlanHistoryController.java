package com.eatWise.controller;

import com.eatWise.config.CustomUserDetailsService;
import com.eatWise.dto.response.PlanResponse;
import com.eatWise.service.PlanHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class PlanHistoryController {

    private final PlanHistoryService planHistoryService;

    @GetMapping
    public ResponseEntity<List<PlanResponse>> getAllUserPlans() {
        List<PlanResponse> plans = planHistoryService.
                getAllUserPlans(CustomUserDetailsService.getAuthentication().getName());
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/{planId}")
    public ResponseEntity<PlanResponse> getPlanById(@PathVariable Long planId){
        PlanResponse plan = planHistoryService.
                getPlanById(planId, CustomUserDetailsService.getAuthentication().getName());
        return ResponseEntity.ok(plan);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllUserPlans() {
        planHistoryService.deleteAllUserPlans(CustomUserDetailsService.getAuthentication().getName());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long planId) {
        planHistoryService.deletePlanById(planId, CustomUserDetailsService.getAuthentication().getName());
        return ResponseEntity.noContent().build();
    }
}
