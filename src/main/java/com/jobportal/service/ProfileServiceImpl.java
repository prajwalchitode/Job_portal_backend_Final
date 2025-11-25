package com.jobportal.service;

import com.jobportal.dto.ProfileDTO;
import com.jobportal.entity.Profile;
import com.jobportal.entity.User;
import com.jobportal.exception.JobPortalException;
import com.jobportal.repository.ProfileRepository;
import com.jobportal.repository.UserRepository;
import com.jobportal.utility.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service("profileService")
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Long createProfile(String email) throws JobPortalException {
        Profile profile = new Profile();
        profile.setId(Utilities.getNextSequence("profiles"));
        profile.setEmail(email);
        profile.setSkills(new ArrayList<>());
        profile.setExperiences(new ArrayList<>());
        profile.setCertifications(new ArrayList<>());
        profileRepository.save(profile);
        return profile.getId();
    }

    @Override
    public ProfileDTO getProfile(Long id) throws JobPortalException {
     return  profileRepository.findById(id).orElseThrow(() -> new JobPortalException("PROFILE_NOT_FOUND")).toDto();
    }

    @Override
    public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobPortalException {
          profileRepository.findById(profileDTO.getId()).orElseThrow(() -> new JobPortalException("PROFILE_NOT_FOUND"));
        Optional<User> user = userRepository.findByEmail(profileDTO.getEmail());
        if (user.isPresent()) {
            profileDTO.setName(user.get().getName());   // <-- Set the user name here
        }
          profileRepository.save(profileDTO.toEntity());
          return profileDTO;
    }

    @Override
    public List<ProfileDTO> getAllProfile() {
        return profileRepository.findAll().stream().map((x)-> x.toDto()).toList();
    }

    @Override
    public Optional<User> getProfileId(long id) {
//        UserDto dto = userRepository.findById(id).orElseThrow(()-> new JobPortalException("User Not Found")).toDto();
//            return dto;
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return  user;
        }
        return null;
    }


}
