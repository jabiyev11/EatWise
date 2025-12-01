package com.eatWise.service;

import com.eatWise.domain.Profile;
import com.eatWise.domain.User;
import com.eatWise.dto.request.ProfileRequest;
import com.eatWise.dto.response.ProfileResponse;
import com.eatWise.exception.ProfileException;
import com.eatWise.mapper.ProfileMapper;
import com.eatWise.repository.ProfileRepository;
import com.eatWise.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ProfileMapper profileMapper;

    @Transactional
    public ProfileResponse createProfile(Long userId, @Valid ProfileRequest request) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ProfileException("User not found with ID: " + userId));

        if (profileRepository.existsByUserId(userId)) {
            throw new ProfileException.ProfileAlreadyExistsException(
                    "Profile already exists for user ID: " + userId);
        }

        Profile profile = profileMapper.toEntity(request);
        profile.setUser(user);

        Profile savedProfile = profileRepository.save(profile);
        log.info("Profile created for user Id: {}", userId);

        return profileMapper.toResponse(savedProfile);
    }

    @Transactional(readOnly = true)
    public ProfileResponse getProfileByUserId(Long userId) {
        log.info("Fetching profile for user ID: {}", userId);

        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileException.ProfileNotFoundException(
                        "Profile not found for user ID: " + userId));

        return profileMapper.toResponse(profile);
    }

    @Transactional
    public ProfileResponse updateProfile(Long userId, @Valid ProfileRequest request) {
        Profile profile = profileRepository.findByUserId(userId).orElseThrow(() -> new ProfileException.ProfileNotFoundException("Profile not found for user Id: " + userId));

        profileMapper.updateEntityFromRequest(profile, request);

        Profile updatedProfile = profileRepository.save(profile);
        log.info("Profile updated for user Id: {}", userId);
        return profileMapper.toResponse(updatedProfile);
    }

    @Transactional
    public void deleteProfile(Long userId) {
        if(!profileRepository.existsByUserId(userId)) {
            log.info("Profile not found for user Id: {}", userId);
            throw new ProfileException.ProfileNotFoundException("Profile not found for user Id: " + userId);
        }

        profileRepository.deleteByUserId(userId);
        log.info("Profile deleted from user Id: {}", userId);
    }

    @Transactional(readOnly = true)
    public boolean hasCompleteProfile(Long userId) {
        return profileRepository.findByUserId(userId)
                .map(Profile::getIsComplete)
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public Profile getProfileEntity(Long userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileException.ProfileNotFoundException(
                        "Profile not found for user ID: " + userId));
    }


}
