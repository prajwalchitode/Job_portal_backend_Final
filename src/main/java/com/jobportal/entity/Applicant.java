package com.jobportal.entity;

import com.jobportal.dto.ApplicantDTO;
import com.jobportal.dto.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Base64;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Applicant {
    private Long applicationId;
    private String name;
    private String email;
    private Long phone;
    private String website;
    private byte[] resume;
    private String coverLetter;
    private LocalDateTime timeStamp;
    private ApplicationStatus applicationStatus;
    private LocalDateTime interviewTime;
    public ApplicantDTO toDTO(){
        return new ApplicantDTO(this.applicationId,this.name,this.email,this.phone,this.website,
                this.resume!=null ? Base64.getEncoder().encodeToString(this.resume):null,this.coverLetter,this.timeStamp,this.applicationStatus,this.interviewTime);
    }
}
