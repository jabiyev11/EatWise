package com.eatWise.controller;

import com.eatWise.config.CustomUserDetailsService;
import com.eatWise.domain.User;
import com.eatWise.dto.request.ProfileRequest;
import com.eatWise.dto.response.ProfileResponse;
import com.eatWise.dto.response.RestResponse;
import com.eatWise.repository.UserRepository;
import com.eatWise.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<RestResponse<ProfileResponse>> createProfile(@Valid @RequestBody ProfileRequest request) {
        String email = CustomUserDetailsService.getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Long userId = user.getId();

        ProfileResponse response = profileService.createProfile(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestResponse.success(response, "Profile created successfully"));
    }

    @GetMapping
    public ResponseEntity<RestResponse<ProfileResponse>> getProfile() {
        String email = CustomUserDetailsService.getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Long userId = user.getId();
        log.info("Get profile - userId: {}", userId);

        ProfileResponse response = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok(RestResponse.success(response));
    }

    @PutMapping
    public ResponseEntity<RestResponse<ProfileResponse>> updateProfile(@Valid @RequestBody ProfileRequest request) {
        String email = CustomUserDetailsService.getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Long userId = user.getId();
        log.info("Profile update - userId: {}", userId);

        ProfileResponse response = profileService.updateProfile(userId, request);
        return ResponseEntity.ok(
                RestResponse.success(response, "Profile updated successfully"));
    }

    @DeleteMapping
    public ResponseEntity<RestResponse<Void>> deleteProfile() {
        String email = CustomUserDetailsService.getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Long userId = user.getId();
        log.info("Profile delete - userId: {}", userId);

        profileService.deleteProfile(userId);
        return ResponseEntity.ok(
                RestResponse.success(null, "Profile deleted successfully"));
    }

    @GetMapping("/status")
    public ResponseEntity<RestResponse<Boolean>> checkProfileStatus() {
        String email = CustomUserDetailsService.getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Long userId = user.getId();

        log.info("Check profile status - userId: {}", userId);

        boolean hasProfile = profileService.hasCompleteProfile(userId);
        return ResponseEntity.ok(RestResponse.success(hasProfile));
    }
}
