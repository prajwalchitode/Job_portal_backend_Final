package com.jobportal.repository;

import com.jobportal.entity.FeedbackDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedbackRepo extends MongoRepository<FeedbackDto,String> {
}
