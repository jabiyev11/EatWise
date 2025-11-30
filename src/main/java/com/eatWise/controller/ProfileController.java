package com.eatWise.controller;

import com.eatWise.domain.Profile;
import com.eatWise.dto.request.ProfileRequest;
import com.eatWise.dto.response.ProfileResponse;
import com.eatWise.dto.response.RestResponse;
import com.eatWise.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;


    @PostMapping
    public ResponseEntity<RestResponse<ProfileResponse>> createProfile(@Valid @RequestBody ProfileRequest request) {
        Long userId = getCurrentUserId();
        log.info("User create: {}", userId);

        ProfileResponse response = profileService.createProfile(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(RestResponse.success(response, "Profile created successfully"));
    }

    @GetMapping
    public ResponseEntity<RestResponse<ProfileResponse>> getProfile() {
        Long userId = getCurrentUserId();
        log.info("Get user: {}", userId);

        ProfileResponse response = profileService.getProfileByUserId(userId);

        return ResponseEntity.ok(RestResponse.success(response));
    }

    @PutMapping
    public ResponseEntity<RestResponse<ProfileResponse>> updateProfile(@Valid @RequestBody ProfileRequest request) {
        Long userId = getCurrentUserId();
        log.info("Profile update: {}", userId);

        ProfileResponse response = profileService.updateProfile(userId, request);

        return ResponseEntity.ok(RestResponse.success(response, "Profile updated successfully"));
    }

    @DeleteMapping
    public ResponseEntity<RestResponse<Void>> deleteProfile() {
        Long userId = getCurrentUserId();
        log.info("Profile delete: {}", userId);

        profileService.deleteProfile(userId);

        return ResponseEntity.ok(RestResponse.success(null, "Profile deleted successfully"));
    }

    @GetMapping("/status")
    public ResponseEntity<RestResponse<Boolean>> checkProfileStatus() {
        Long userId = getCurrentUserId();
        boolean hasProfile = profileService.hasCompleteProfile(userId);
        return ResponseEntity.ok(RestResponse.success(hasProfile));
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Long.parseLong(authentication.getName());
    }
}
