package com.eatWise.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UnauthorizedException extends RuntimeException {
    private final String message;
}
