package com.jobportal.service;

import com.jobportal.entity.FeedbackDto;
import com.jobportal.repository.FeedbackRepo;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService{

    public FeedbackRepo repo;
    FeedbackServiceImpl(FeedbackRepo repo){
        this.repo = repo;
    }

    @Override
    public FeedbackDto postFeedback(FeedbackDto dto) {
      dto.setCreatedAt(System.currentTimeMillis());
      return repo.save(dto);
    }
}
