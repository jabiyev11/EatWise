package com.eatWise.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse<T> {
    private boolean success;
    private String message;
    private T data;

  public static <T> RestResponse<T> ok(@Nullable T data) {
    RestResponse<T> response = ok();
    response.data = data;
    return response;
  }

    public static <T> RestResponse<T> success(T data) {
        return RestResponse.<T>builder()
                .success(true)
                .data(data)
                .build();
    }

    public static <T> RestResponse<T> success(T data, String message) {
        return RestResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> RestResponse<T> ok() {
        return new RestResponse<>();
    }
}

