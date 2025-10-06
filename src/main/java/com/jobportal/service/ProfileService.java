package com.jobportal.service;

import com.jobportal.dto.ProfileDTO;
import com.jobportal.exception.JobPortalException;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;


public interface ProfileService {
    public Long createProfile(String email) throws JobPortalException;

    public ProfileDTO getProfile(Long id) throws JobPortalException;

    public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobPortalException;

    List<ProfileDTO> getAllProfile();
}
