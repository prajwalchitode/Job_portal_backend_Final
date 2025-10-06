package com.jobportal.service;

import com.jobportal.dto.LoginDto;
import com.jobportal.dto.NotificationDTO;
import com.jobportal.dto.ResponseDto;
import com.jobportal.dto.UserDto;
import com.jobportal.entity.OTP;
import com.jobportal.entity.User;
import com.jobportal.exception.JobPortalException;
import com.jobportal.repository.NotificationRepository;
import com.jobportal.repository.OtpRepository;
import com.jobportal.repository.ProfileRepository;
import com.jobportal.repository.UserRepository;
import com.jobportal.utility.Data;
import com.jobportal.utility.Utilities;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public UserDto registerUser(UserDto userDto) throws JobPortalException {
        Optional<User> optional = userRepository.findByEmail(userDto.getEmail());
        if (optional.isPresent()) throw new JobPortalException("USER_FOUND");
        userDto.setProfileId(profileService.createProfile(userDto.getEmail()));
        userDto.setId(Utilities.getNextSequence("users"));
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userDto.toEntity();
        user = userRepository.save(user);
        return user.toDto();
    }

    @Override
    public UserDto getUserByEmail(String email) throws JobPortalException {
        return userRepository.findByEmail(email).orElseThrow(() -> new JobPortalException("USER_NOT_FOUNd")).toDto();
    }

    @Override
    public UserDto loginUser(LoginDto loginDto) throws JobPortalException {

        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
            throw new JobPortalException("INVALID_CREDENTIALS");
        return user.toDto();
    }

    @Override
    public boolean sendOtp(String email) throws Exception {
      User user =   userRepository.findByEmail(email).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
        MimeMessage mm = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mm,true);
        message.setTo(email);
        message.setSubject("Your OTP Code");
        String otpp = Utilities.generateOtp();
        OTP otp = new OTP(email,otpp, LocalDateTime.now());
        otpRepository.save(otp);
        message.setText(Data.getMessageBody(otpp,user.getName()),true);
        mailSender.send(mm);
        return true;
    }

    @Override
    public boolean verifyOtp(String email, String otp) throws JobPortalException {
        OTP otpEntity = otpRepository.findById(email).orElseThrow(()-> new JobPortalException("OTP_NOT_FOUND"));
        if(!otpEntity.getOtpCode().equals(otp)){
            throw new JobPortalException("OTP_INCORRECT");
        }
        return true;
    }

    @Override
    public ResponseDto changePassword(LoginDto loginDto) throws JobPortalException {

        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new JobPortalException("USER_NOT_FOUND"));
       user.setPassword(passwordEncoder.encode(loginDto.getPassword()));
       userRepository.save(user);
       NotificationDTO noti = new NotificationDTO();
       noti.setUserId(user.getId());
       noti.setMessage("Password Reset Successfully");
       noti.setAction("Password");
        notificationService.sendNotification(noti);
        return new ResponseDto("Password changed successfully.");
    }

    @Scheduled(fixedRate = 60000)
    public void removeExpiredOTP(){
        LocalDateTime exp = LocalDateTime.now().minusMinutes(5);
      List<OTP> expiredOtp =  otpRepository.findByCreationTimeBefore(exp);
      if(!expiredOtp.isEmpty()){
        otpRepository.deleteAll(expiredOtp);
          System.out.println("Removed "+expiredOtp.size()+ " ___________________");
      }
    }
}
