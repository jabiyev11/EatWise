package com.eatWise.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthException extends RuntimeException {

    private final String message;

}
