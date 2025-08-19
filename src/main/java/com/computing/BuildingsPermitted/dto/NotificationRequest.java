package com.computing.BuildingsPermitted.dto;

import jakarta.validation.constraints.NotBlank;

public class NotificationRequest {
    @NotBlank
    private String subject;
    @NotBlank
    private String message;
    // optional target filters: role or email
    private String role;
    private String email;

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
