package com.jobportal.service;

import com.jobportal.dto.NotificationDTO;
import com.jobportal.entity.Notification;

import java.util.List;

public interface NotificationService {
    public void sendNotification(NotificationDTO notificationDTO);

    public List<Notification> getUnreadNotifications(Long userId);

    void readNotifications(Long id);
}
