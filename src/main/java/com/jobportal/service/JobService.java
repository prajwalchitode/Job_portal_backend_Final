package com.jobportal.service;

import com.jobportal.dto.ApplicantDTO;
import com.jobportal.dto.Application;
import com.jobportal.dto.JobDTO;
import com.jobportal.dto.ResponseDto;
import com.jobportal.exception.JobPortalException;

import java.util.List;

public interface JobService {

    JobDTO postJob(JobDTO jobDto) throws JobPortalException;

    List<JobDTO> getAllJob();

    JobDTO getJobByids(Long id);

    ResponseDto applyJob(Long id, ApplicantDTO applicantDTO) throws JobPortalException;

    public List<JobDTO> getJobsPostedBy(Long id);

    void changeAppStatus(Application application) throws JobPortalException;
}
