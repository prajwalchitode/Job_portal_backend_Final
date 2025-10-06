package com.jobportal.api;

import com.jobportal.dto.*;
import com.jobportal.exception.JobPortalException;
import com.jobportal.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/jobs")
public class JobAPI {

    @Autowired
    private JobService jobService;

    @PostMapping("/post")
    public ResponseEntity<JobDTO> postJob(@RequestBody @Valid JobDTO jobDto) throws JobPortalException {
        return new ResponseEntity<>(jobService.postJob(jobDto), HttpStatus.CREATED);
    }

//    @PostMapping("/post")
//    public ResponseEntity<JobDTO> postJob(@RequestBody @Valid JobDTO jobDto) throws JobPortalException {
//        return new ResponseEntity<>(jobService.postJob(jobDto), HttpStatus.CREATED);
//    }

    @PostMapping("/changeAppStatus")
    public ResponseEntity<ResponseDto> changeAppStatus(@RequestBody Application application) throws JobPortalException {
        jobService.changeAppStatus(application);
        return new ResponseEntity<>(new ResponseDto("Status Updated Successfully"), HttpStatus.OK);
    }

    @GetMapping("/getJobs")
    public ResponseEntity <List<JobDTO>> getAllJob() throws JobPortalException {
        return new ResponseEntity<>(jobService.getAllJob(), HttpStatus.CREATED);
    }

    @GetMapping("/getJobsById/{id}")
    public ResponseEntity <JobDTO> getJobByids(@PathVariable Long id) throws JobPortalException {
        return new ResponseEntity<>(jobService.getJobByids(id), HttpStatus.CREATED);
    }

    @GetMapping("/postedBy/{id}")
    public ResponseEntity<List<JobDTO>> getJobsPostedBy(@PathVariable Long id) throws JobPortalException {
        return new ResponseEntity<>(jobService.getJobsPostedBy(id), HttpStatus.CREATED);
    }

    @PostMapping("/apply/{id}")
    public ResponseEntity<ResponseDto> applyJob(@PathVariable Long id, @RequestBody ApplicantDTO applicantDTO) throws JobPortalException{
        return new ResponseEntity<>(jobService.applyJob(id,applicantDTO), HttpStatus.CREATED);
    }

}
