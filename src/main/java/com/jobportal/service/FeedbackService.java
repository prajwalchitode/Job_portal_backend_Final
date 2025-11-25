package com.jobportal.service;

import com.jobportal.entity.FeedbackDto;

public interface FeedbackService {
    FeedbackDto postFeedback(FeedbackDto dto);
}
