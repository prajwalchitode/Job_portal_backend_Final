package com.jobportal.api;

import com.jobportal.entity.FeedbackDto;
import com.jobportal.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/feedback")

public class FeedbackController {

    @Autowired
    public FeedbackService service;

    FeedbackController(FeedbackService service){
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<?> postFeedback(@RequestBody FeedbackDto dto){
        try{
            FeedbackDto mess = service.postFeedback(dto);
            return ResponseEntity.ok(mess);
        }catch (Exception e){
            return ResponseEntity.status(500).body("Failed to save message");
        }
    }
}
