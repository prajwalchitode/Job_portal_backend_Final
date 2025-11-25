package com.jobportal.service;

import com.jobportal.dto.ProfileDTO;
import com.jobportal.entity.User;
import com.jobportal.exception.JobPortalException;

import java.util.List;
import java.util.Optional;


public interface ProfileService {
    public Long createProfile(String email) throws JobPortalException;

    public ProfileDTO getProfile(Long id) throws JobPortalException;

    public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobPortalException;

    List<ProfileDTO> getAllProfile();

    public Optional<User> getProfileId(long id);
}
