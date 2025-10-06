package com.jobportal.entity;

import com.jobportal.dto.NotificationDTO;
import com.jobportal.dto.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "notification")
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private Long id;
    private Long userId;
    private String message;
    private String action;
    private String route;
    private NotificationStatus status;
    private LocalDateTime timeStamp;

    public NotificationDTO toDTO(){
        return new NotificationDTO(this.id,this.userId,this.message,this.action,this.route,this.status,this.timeStamp);
    }
}
