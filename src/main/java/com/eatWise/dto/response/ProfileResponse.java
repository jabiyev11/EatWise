
package com.eatWise.dto.response;

import com.eatWise.domain.Profile.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private Long id;
    private Long userId;
    private Integer age;
    private Double weight;
    private Double height;
    private Gender gender;
    private ActivityLevel activityLevel;
    private Goal goal;
    private DietaryPreference dietaryPreference;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}