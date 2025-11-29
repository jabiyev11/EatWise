package com.eatWise.dto.request;

import com.eatWise.domain.Profile.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {

    @NotNull(message = "Age is required")
    @Min(value = 13, message = "Age must be at least 13")
    @Max(value = 100, message = "Age must be less than 100")
    private Integer age;

    @NotNull(message = "Weight is required")
    @DecimalMin(value = "30.0", message = "Weight must be more than 30")
    @DecimalMax(value = "300.0", message = "Weight must be less than 300")
    private Double weight;

    @NotNull(message = "Height is required")
    @DecimalMin(value = "100.0", message = "Height must be more than 100 cm")
    @DecimalMax(value = "250.0", message = "Height must be less than 250 cm")
    private Double height;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Activity level is required")
    private ActivityLevel activityLevel;

    @NotNull(message = "Goal is required")
    private Goal goal;

    @NotNull(message = "Dietary preference is required")
    private DietaryPreference dietaryPreference;
}
