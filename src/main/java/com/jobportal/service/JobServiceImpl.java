package com.jobportal.service;

import com.jobportal.dto.*;
import com.jobportal.entity.Applicant;
import com.jobportal.entity.Job;
import com.jobportal.exception.JobPortalException;
import com.jobportal.repository.JobsRepository;
import com.jobportal.utility.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("jobService")
public class JobServiceImpl implements JobService{

    @Autowired
    private JobsRepository jobsRepository;

    @Autowired
    private NotificationService service;

    @Override
    public  JobDTO postJob(JobDTO jobDto) throws JobPortalException {
        if(jobDto.getId() == 0) {
            jobDto.setId(Utilities.getNextSequence("jobs"));
            jobDto.setPostTime(LocalDateTime.now());
            NotificationDTO noti = new NotificationDTO();
            noti.setAction("Job Posted");
            noti.setMessage("Job Posted Successfully for "+jobDto.getJobTitle()+" at " + jobDto.getCompany());
            noti.setUserId(jobDto.getPostedBy());
            noti.setRoute("/posted-job/"+jobDto.getId());
            service.sendNotification(noti);
        }else{
            Job job = jobsRepository.findById(jobDto.getId()).orElseThrow(() -> new JobPortalException("JOB_NOT_FOUND"));
            if(job.getJobStatus().equals(JobStatus.DRAFT) || jobDto.getJobStatus().equals(JobStatus.CLOSED) ){
                jobDto.setPostTime(LocalDateTime.now());
            }
        }
        return jobsRepository.save(jobDto.toEntity()).toDTO();
    }

    @Override
    public List<JobDTO> getAllJob() {
      return jobsRepository.findAll().stream().map((x)-> x.toDTO()).toList();
    }

    @Override
    public JobDTO getJobByids(Long id) throws JobPortalException {
        return jobsRepository.findById(id).orElseThrow(() -> new JobPortalException("JOB_NOT_FOUND")).toDTO();
    }

    @Override
    public ResponseDto applyJob(Long id, ApplicantDTO applicantDTO) throws JobPortalException {
        Job job =  jobsRepository.findById(id).orElseThrow(() -> new JobPortalException("JOB_NOT_FOUND"));
        List<Applicant> applicants = job.getApplicants();
        if(applicants==null) applicants=new ArrayList<>();
        if(applicants.stream().filter((x)-> x.getApplicationId() == applicantDTO.getApplicationId()).toList().size()>0) throw new JobPortalException("JOB_ALREADY_APPLIED");
        applicantDTO.setApplicationStatus(ApplicationStatus.APPLIED);
        applicants.add(applicantDTO.toEntity());
        job.setApplicants(applicants);
        jobsRepository.save(job);
        return null;
    }

    @Override
    public List<JobDTO> getJobsPostedBy(Long id) {
        return jobsRepository.findByPostedBy(id).stream().map((x)-> x.toDTO()).toList();
    }

    @Override
    public void changeAppStatus(Application application) throws JobPortalException {
        Job job =  jobsRepository.findById(application.getId()).orElseThrow(() -> new JobPortalException("JOB_NOT_FOUND"));
        List<Applicant> applicants = job.getApplicants().stream().map((x)-> {
            if(application.getApplicantId() == x.getApplicationId()) {
                x.setApplicationStatus(application.getApplicationStatus());
                if (application.getApplicationStatus().equals(ApplicationStatus.INTERVIEWING))
                    x.setInterviewTime(application.getInterviewTime());
                    NotificationDTO noti = new NotificationDTO();
                    noti.setAction("Interview Sheduled");
                    noti.setMessage("Interview Sheduled For job id: " + application.getId());
                    noti.setUserId(application.getApplicantId());
                    noti.setRoute("/job-history");
                    service.sendNotification(noti);
            }
                return x;

        }).toList();
        job.setApplicants(applicants);
        jobsRepository.save(job);
    }
}
