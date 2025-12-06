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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanHistoryService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final PlanMapper planMapper;

    public List<PlanResponse> getAllUserPlans(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        List<Plan> plans = planRepository.findByUserId(user.getId());

        return planMapper.toResponseList(plans);
    }

    public PlanResponse getPlanById(Long planId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFound("Plan not found with id: " + planId));

        if (!(user.getId().equals(plan.getUser().getId()))) {
            throw new UnauthorizedException("You don't have permission to access this plan");
        }

        return planMapper.toPlanResponse(plan);
    }

    @Transactional
    public void deletePlanById(Long planId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFound("Plan not found with id: " + planId));

        if (!(user.getId().equals(plan.getUser().getId()))) {
            throw new UnauthorizedException("You don't have permission to delete this plan");
        }

        planRepository.delete(plan);
    }

    @Transactional
    public void deleteAllUserPlans(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        List<Plan> plans = planRepository.findByUserId(user.getId());

        planRepository.deleteAll(plans);
    }
}
