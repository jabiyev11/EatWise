package com.eatWise.controller;

import com.eatWise.dto.response.PlanResponse;
import com.eatWise.service.PlanHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.eatWise.config.CustomUserDetailsService.getAuthentication;

@Slf4j
@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class PlanHistoryController {

    private final PlanHistoryService planHistoryService;

    @GetMapping
    public ResponseEntity<List<PlanResponse>> getAllUserPlans() {
        String email = getAuthentication().getName();
        log.info("GET /api/history - Fetching all plans for user: {}", email);

        List<PlanResponse> plans = planHistoryService.getAllUserPlans(email);

        log.info("GET /api/history - Successfully retrieved {} plans for user: {}", plans.size(), email);
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/{planId}")
    public ResponseEntity<PlanResponse> getPlanById(@PathVariable Long planId){
        String email = getAuthentication().getName();
        log.info("GET /api/history/{} - Fetching plan for user: {}", planId, email);

        PlanResponse plan = planHistoryService.getPlanById(planId, email);

        log.info("GET /api/history/{} - Successfully retrieved plan for user: {}", planId, email);
        return ResponseEntity.ok(plan);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllUserPlans() {
        String email = getAuthentication().getName();
        log.info("DELETE /api/history - Deleting all plans for user: {}", email);

        planHistoryService.deleteAllUserPlans(email);

        log.info("DELETE /api/history - Successfully deleted all plans for user: {}", email);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long planId) {
        String email = getAuthentication().getName();
        log.info("DELETE /api/history/{} - Deleting plan for user: {}", planId, email);

        planHistoryService.deletePlanById(planId, email);

        log.info("DELETE /api/history/{} - Successfully deleted plan for user: {}", planId, email);
        return ResponseEntity.noContent().build();
    }
}
