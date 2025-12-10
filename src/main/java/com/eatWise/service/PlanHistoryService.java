package com.eatWise.service;

import com.eatWise.domain.Plan;
import com.eatWise.domain.User;
import com.eatWise.dto.response.PlanResponse;
import com.eatWise.exception.ResourceNotFound;
import com.eatWise.exception.UnauthorizedException;
import com.eatWise.mapper.PlanMapper;
import com.eatWise.repository.PlanRepository;
import com.eatWise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanHistoryService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final PlanMapper planMapper;

    public List<PlanResponse> getAllUserPlans(String email) {
        log.debug("Fetching all plans for user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", email);
                    return new ResourceNotFound("User not found");
                });

        List<Plan> plans = planRepository.findByUserId(user.getId());

        log.info("Found {} plans for user: {} (userId: {})", plans.size(), email, user.getId());
        return planMapper.toResponseList(plans);
    }

    public PlanResponse getPlanById(Long planId, String email) {
        log.debug("Fetching plan with id: {} for user: {}", planId, email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", email);
                    return new ResourceNotFound("User not found");
                });

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> {
                    log.error("Plan not found with id: {} for user: {}", planId, email);
                    return new ResourceNotFound("Plan not found with id: " + planId);
                });

        if (!(user.getId().equals(plan.getUser().getId()))) {
            log.warn("Unauthorized access attempt: User {} (userId: {}) tried to access plan {} owned by userId: {}",
                    email, user.getId(), planId, plan.getUser().getId());
            throw new UnauthorizedException("You don't have permission to access this plan");
        }

        log.info("Successfully fetched plan {} for user: {}", planId, email);
        return planMapper.toPlanResponse(plan);
    }

    @Transactional
    public void deletePlanById(Long planId, String email) {
        log.debug("Attempting to delete plan {} for user: {}", planId, email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", email);
                    return new ResourceNotFound("User not found");
                });

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> {
                    log.error("Plan not found with id: {} for user: {}", planId, email);
                    return new ResourceNotFound("Plan not found with id: " + planId);
                });

        if (!(user.getId().equals(plan.getUser().getId()))) {
            log.warn("Unauthorized deletion attempt: User {} (userId: {}) tried to delete plan {} owned by userId: {}",
                    email, user.getId(), planId, plan.getUser().getId());
            throw new UnauthorizedException("You don't have permission to delete this plan");
        }

        planRepository.delete(plan);
        log.info("Successfully deleted plan {} for user: {}", planId, email);
    }

    @Transactional
    public void deleteAllUserPlans(String email) {
        log.debug("Attempting to delete all plans for user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", email);
                    return new ResourceNotFound("User not found");
                });

        List<Plan> plans = planRepository.findByUserId(user.getId());

        if (plans.isEmpty()) {
            log.info("No plans to delete for user: {} (userId: {})", email, user.getId());
            return;
        }

        planRepository.deleteAll(plans);
        log.info("Successfully deleted {} plans for user: {} (userId: {})", plans.size(), email, user.getId());
    }
}