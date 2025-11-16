package com.eatWise.dto.response;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class RestResponse<T> {

  private T data;

  public static <T> RestResponse<T> ok(@Nullable T data) {
    RestResponse<T> response = ok();
    response.data = data;
    return response;
  }

  public static <T> RestResponse<T> ok() {
    return new RestResponse<>();
  }

}

