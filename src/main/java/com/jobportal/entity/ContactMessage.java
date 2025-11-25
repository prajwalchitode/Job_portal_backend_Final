package com.jobportal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "contact_messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactMessage {

    @Id
    private String id;
    private String name;
    private String email;
    private String message;
    private long createdAt;
}
