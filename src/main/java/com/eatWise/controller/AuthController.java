package com.eatWise.controller;

import com.eatWise.dto.request.LoginRequest;
import com.eatWise.dto.request.RefreshTokenRequest;
import com.eatWise.dto.request.RegisterRequest;
import com.eatWise.dto.response.AuthResponse;
import com.eatWise.dto.response.RestResponse;
import com.eatWise.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public RestResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return RestResponse.ok(authService.register(request));
    }

    @PostMapping("/login")
    public RestResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return RestResponse.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public RestResponse<AuthResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {
        return RestResponse.ok(authService.refreshToken(request));
    }

    @PostMapping("/logout")
    public RestResponse<Void> logout(Authentication authentication) {
        authService.logout(authentication.getName());
        return RestResponse.ok();
    }

}
