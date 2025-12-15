package com.eatWise.mapper;

import com.eatWise.domain.Profile;
import com.eatWise.dto.request.ProfileRequest;
import com.eatWise.dto.response.ProfileResponse;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public ProfileResponse toResponse(Profile profile) {
        if (profile == null) {
            return null;
        }

        return ProfileResponse.builder()
                .id(profile.getId())
                .userId(profile.getUser().getId())
                .age(profile.getAge())
                .weight(profile.getWeight())
                .height(profile.getHeight())
                .gender(profile.getGender())
                .activityLevel(profile.getActivityLevel())
                .goal(profile.getGoal())
                .dietaryPreference(profile.getDietaryPreference())
                .isComplete(profile.getIsComplete())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
        //        .bmi(calculateBMI(profile.getWeight(), profile.getHeight()))
                //        .dailyCalorieTarget(calculateDailyCalories(profile))
                .build();
    }

    public Profile toEntity(ProfileRequest request) {
        if (request == null) {
            return null;
        }

        return Profile.builder()
                .age(request.getAge())
                .weight(request.getWeight())
                .height(request.getHeight())
                .gender(request.getGender())
                .activityLevel(request.getActivityLevel())
                .goal(request.getGoal())
                .dietaryPreference(request.getDietaryPreference())
                .isComplete(true)
                .build();
    }

    public void updateEntityFromRequest(Profile profile, ProfileRequest request) {
        if (request == null || profile == null) {
            return;
        }

        profile.setAge(request.getAge());
        profile.setWeight(request.getWeight());
        profile.setHeight(request.getHeight());
        profile.setGender(request.getGender());
        profile.setActivityLevel(request.getActivityLevel());
        profile.setGoal(request.getGoal());
        profile.setDietaryPreference(request.getDietaryPreference());
        profile.setIsComplete(true);
    }

    // Helper methods for calculations
    private Double calculateBMI(Double weight, Double height) {
        if (weight == null || height == null || height == 0) {
            return null;
        }
        // BMI = weight(kg) / (height(m))^2
        double heightInMeters = height / 100.0;
        return Math.round(weight / (heightInMeters * heightInMeters) * 10.0) / 10.0;
    }

    private Integer calculateDailyCalories(Profile profile) {
        if (profile == null) {
            return null;
        }

        // Calculate BMR using Mifflin-St Jeor Equation
        double bmr;
        if (profile.getGender() == Profile.Gender.MALE) {
            bmr = (10 * profile.getWeight()) + (6.25 * profile.getHeight())
                    - (5 * profile.getAge()) + 5;
        } else {
            bmr = (10 * profile.getWeight()) + (6.25 * profile.getHeight())
                    - (5 * profile.getAge()) - 161;
        }

        // Apply activity level multiplier
        double activityMultiplier = switch (profile.getActivityLevel()) {
            case SEDENTARY -> 1.2;
            case LIGHT -> 1.375;
            case MODERATE -> 1.55;
            case ACTIVE -> 1.725;
        };

        double tdee = bmr * activityMultiplier;

        // Adjust based on goal
        double targetCalories = switch (profile.getGoal()) {
            case FAT_LOSS -> tdee - 500;  // 500 calorie deficit
            case MAINTENANCE -> tdee;
            case MUSCLE_GAIN -> tdee + 400;  // 400 calorie surplus
        };

        return (int) Math.round(targetCalories);
    }
}