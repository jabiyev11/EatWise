package com.eatWise.client;

import com.eatWise.client.model.request.RagRequest;
import com.eatWise.client.model.response.RagResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ms-rag-service",
        url = "${api.rag.url}")
public interface RagClient {

    @PostMapping("/generate")
    RagResponse generatePlan(RagRequest request);

}
