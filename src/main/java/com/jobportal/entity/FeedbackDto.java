package com.jobportal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "feedback")
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {

    @Id
    private String id;
    private int rating;
    private String feedback;
    private long createdAt;
}
