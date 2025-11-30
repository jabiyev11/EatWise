package com.eatWise.mapper;

import com.eatWise.domain.Profile;
import com.eatWise.dto.request.ProfileRequest;
import com.eatWise.dto.response.ProfileResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProfileMapper1 {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "bmi", expression = "java(calculateBMI(profile.getWeight(), profile.getHeight()))")
    @Mapping(target = "dailyCalorieTarget", expression = "java(calculateDailyCalories(profile))")
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

    // Helper methods for calculations
    default Double calculateBMI(Double weight, Double height) {
        if (weight == null || height == null || height == 0) {
            return null;
        }
        double heightInMeters = height / 100.0;
        return Math.round(weight / (heightInMeters * heightInMeters) * 10.0) / 10.0;
    }

    default Integer calculateDailyCalories(Profile profile) {
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
            case LIGHTLY_ACTIVE -> 1.375;
            case MODERATELY_ACTIVE -> 1.55;
            case VERY_ACTIVE -> 1.725;
            case EXTRA_ACTIVE -> 1.9;
        };

        double tdee = bmr * activityMultiplier;

        // Adjust based on goal
        double targetCalories = switch (profile.getGoal()) {
            case LOSE_WEIGHT -> tdee - 500;
            case MAINTAIN_WEIGHT -> tdee;
            case GAIN_WEIGHT -> tdee + 300;
            case MUSCLE_GAIN -> tdee + 400;
        };

        return (int) Math.round(targetCalories);
    }
}