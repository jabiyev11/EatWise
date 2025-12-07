package com.eatWise.client.model.response;

import lombok.Data;

@Data
public class RagResponse {

    private boolean success;
    private String title;
    private String content;

}
