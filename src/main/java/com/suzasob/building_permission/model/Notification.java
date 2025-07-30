package com.suzasob.building_permission.model;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String message;

    private Date sentAt;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    // Constructors, Getters, Setters
    public Notification() {
    }
    public Notification(Long notificationId, String message, Date sentAt, User recipient) {
        this.notificationId = notificationId;
        this.message = message;
        this.sentAt = sentAt;
        this.recipient = recipient;
    }
    public Long getNotificationId() {
        return notificationId;
    }
    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Date getSentAt() {
        return sentAt;
    }
    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }
    public User getRecipient() {
        return recipient;
    }
    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }
    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", message='" + message + '\'' +
                ", sentAt=" + sentAt +
                ", recipient=" + recipient +
                '}';
    }
}
