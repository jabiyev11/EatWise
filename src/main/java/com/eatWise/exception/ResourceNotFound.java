package com.eatWise.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResourceNotFound extends RuntimeException{
    private final String message;
}
