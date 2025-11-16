package com.eatWise.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RefreshTokenException extends RuntimeException {

    private final String message;

}
