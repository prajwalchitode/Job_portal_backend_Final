package com.jobportal.api;

import com.jobportal.dto.LoginDto;
import com.jobportal.dto.ResponseDto;
import com.jobportal.dto.UserDto;
import com.jobportal.exception.JobPortalException;
import com.jobportal.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/users")
@Validated
public class UserAPi {
    @Autowired
    private UserService userService;
   private UserDto userDtoo;
   private LoginDto loginDto;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid  UserDto userDto) throws JobPortalException {
        userDtoo  = userService.registerUser(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody @Valid LoginDto loginDto) throws JobPortalException {
        UserDto UserDtoo = userService.loginUser(loginDto);
        return new ResponseEntity<>(UserDtoo, HttpStatus.CREATED);
    }
    
    @PostMapping("/sendOtp/{email}")
    public ResponseEntity<ResponseDto> sendOtp(@PathVariable @Email(message = "{user.email.invalid}") String email) throws Exception {
      boolean otpSend =  userService.sendOtp(email);
        return new ResponseEntity<>( new ResponseDto("Otp sent successfully."),  HttpStatus.CREATED);
    }

    @GetMapping("/verifyOtp/{email}/{otp}")
    public ResponseEntity<ResponseDto> verifyOtp(@PathVariable @Email(message = "{user.email.invalid}") String email , @PathVariable @Pattern(regexp ="^[0-9]{6}$",message ="{otp.invalid}" ) String otp) throws JobPortalException {
      boolean otpSend =  userService.verifyOtp(email,otp);
        return new ResponseEntity<>( new ResponseDto("Otp has been verified"),  HttpStatus.OK);
    }

    @PostMapping("/changepassword")
    public ResponseEntity<ResponseDto> changePassword(@RequestBody @Valid LoginDto loginDto) throws JobPortalException {
        return new ResponseEntity<>(userService.changePassword(loginDto), HttpStatus.OK);
    }

}
