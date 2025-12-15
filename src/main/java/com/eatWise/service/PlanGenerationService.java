package com.eatWise.service;

import static com.eatWise.config.CustomUserDetailsService.getAuthentication;

import com.eatWise.client.RagClient;
import com.eatWise.client.model.request.RagRequest;
import com.eatWise.client.model.request.RagWrapper;
import com.eatWise.client.model.response.RagResponse;
import com.eatWise.domain.Plan;
import com.eatWise.domain.Profile;
import com.eatWise.domain.User;
import com.eatWise.dto.request.DaysRequest;
import com.eatWise.mapper.GeneratedPlanMapper;
import com.eatWise.repository.PlanRepository;
import com.eatWise.repository.ProfileRepository;
import com.eatWise.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanGenerationService {

    private final RagClient ragClient;
    private final GeneratedPlanMapper generatedPlanMapper;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    public RagResponse generatePlan(DaysRequest daysRequest) {

        String email = getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(email);
        Optional<Profile> profile = profileRepository.findByUserId(user.get().getId());

        RagRequest ragRequest = generatedPlanMapper.toRagRequest(profile.get(), daysRequest);
        String fullName = user.get().getFirstName() + " " + user.get().getLastName();
        RagWrapper reg = RagWrapper.builder()
                .fullName(fullName)
                .profile(ragRequest)
                .build();

        RagResponse ragResponse = ragClient.generatePlan(reg);

        log.info("Log: " +  ragResponse);

        Plan plan = generatedPlanMapper.toPlan(ragResponse);

        plan.setUser(user.get());
        planRepository.save(plan);

        return ragResponse;
    }

}
