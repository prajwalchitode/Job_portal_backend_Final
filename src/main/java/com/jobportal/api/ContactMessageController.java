package com.jobportal.api;

import com.jobportal.entity.ContactMessage;
import com.jobportal.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/contact")

public class ContactMessageController {

    private final ContactMessageService service;
    public ContactMessageController(ContactMessageService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createMessage(@RequestBody ContactMessage message) {
        try {
            ContactMessage saved = service.saveMessage(message);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to save message");
        }
    }

}
