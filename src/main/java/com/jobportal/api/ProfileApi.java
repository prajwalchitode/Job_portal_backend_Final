package com.jobportal.api;

import com.jobportal.dto.JobDTO;
import com.jobportal.dto.ProfileDTO;
import com.jobportal.dto.ResponseDto;
import com.jobportal.exception.JobPortalException;
import com.jobportal.service.ProfileService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/profiles")
@CrossOrigin(origins = "http://localhost:3000")
public class ProfileApi {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/get/{id}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long id) throws JobPortalException {
        return new ResponseEntity<>( profileService.getProfile(id),  HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity <List<ProfileDTO>> getAllProfile() throws JobPortalException {
        return new ResponseEntity<>(profileService.getAllProfile(), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ProfileDTO> updateProfile(@RequestBody ProfileDTO profileDTO) throws JobPortalException {
        return new ResponseEntity<>( profileService.updateProfile(profileDTO),  HttpStatus.OK);
    }

    @GetMapping("/getProfileId/{id}")
    public ResponseEntity<?> getProfileId(@PathVariable Long id){
        return new ResponseEntity<>(profileService.getProfileId(id),HttpStatus.OK);
    }
}
