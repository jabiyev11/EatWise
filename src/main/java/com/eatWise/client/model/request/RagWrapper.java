package com.eatWise.client.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RagWrapper {

    private RagRequest profile;
    @JsonProperty("full_name")
    private String fullName;

}
