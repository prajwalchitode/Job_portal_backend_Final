package com.jobportal.repository;
import com.jobportal.entity.ContactMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactMessageRepo extends MongoRepository<ContactMessage,String> {
}
