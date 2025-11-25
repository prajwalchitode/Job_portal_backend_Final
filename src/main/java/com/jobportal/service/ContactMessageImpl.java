package com.jobportal.service;

import com.jobportal.entity.ContactMessage;
import com.jobportal.repository.ContactMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactMessageImpl implements ContactMessageService {
    @Autowired
    private  ContactMessageRepo repo;

    public ContactMessageImpl(ContactMessageRepo repo){
        this.repo = repo;
    }


    public ContactMessage saveMessage(ContactMessage msg) {
        msg.setCreatedAt(System.currentTimeMillis());
        return repo.save(msg);
    }
}
