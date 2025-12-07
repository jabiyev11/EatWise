package com.eatWise.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DaysRequest {

    @Positive
    @Min(value = 1, message = "Minimum days for diet plan is 1")
    @Max(value = 30, message = "Maximum days for diet plan is 30")
    private Integer days;

}
