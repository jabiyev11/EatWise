package com.eatWise.client.model.request;

import com.eatWise.domain.Profile;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RagRequest {

    private Integer age;
    private Profile.Gender gender;
    @JsonProperty("height_cm")
    private Double height;
    @JsonProperty("weight_kg")
    private Double weight;
    private Profile.ActivityLevel activity;
    private Profile.Goal goal;
    @JsonProperty("diet_type")
    private Profile.DietaryPreference dietType;
    private Integer days;

}
