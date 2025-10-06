package com.jobportal.service;

import com.jobportal.dto.LoginDto;
import com.jobportal.dto.ResponseDto;
import com.jobportal.dto.UserDto;
import com.jobportal.entity.User;
import com.jobportal.exception.JobPortalException;
import jakarta.mail.MessagingException;
import lombok.extern.java.Log;
import org.springframework.boot.autoconfigure.batch.BatchProperties;

public interface UserService {

    public UserDto registerUser(UserDto userDto) throws JobPortalException;

    public UserDto getUserByEmail(String email) throws JobPortalException;

    public UserDto loginUser(LoginDto loginDto) throws JobPortalException;

    boolean sendOtp(String email) throws Exception, MessagingException;

    boolean verifyOtp(String email,String otp) throws JobPortalException;

    ResponseDto changePassword(LoginDto loginDto) throws JobPortalException;
}
