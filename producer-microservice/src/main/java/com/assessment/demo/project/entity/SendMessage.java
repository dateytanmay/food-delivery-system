package com.assessment.demo.project.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "send_msg")
public class SendMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ack_id;
    private Long accountId;
    private String mobile;
    private String message;
    private String status;
    private String telcoResponse;
    private LocalDateTime origints;
    private LocalDateTime receivedTs;
    private LocalDateTime sentTs;
    public SendMessage() {};

    public SendMessage(String ack_id, Long accountId, String mobile, String message, String status, LocalDateTime origints, String telcoResponse, LocalDateTime receivedTs) {
        this.ack_id = ack_id;
        this.accountId = accountId;
        this.mobile = mobile;
        this.message = message;
        this.status = status;
        this.origints = origints;
        this.telcoResponse = telcoResponse;
        this.receivedTs = receivedTs;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOrigints() {
        return origints;
    }

    public void setOrigints(LocalDateTime origints) {
        this.origints = origints;
    }

    public String getTelcoResponse() {
        return telcoResponse;
    }

    public void setTelcoResponse(String telcoResponse) {
        this.telcoResponse = telcoResponse;
    }

    public String getAck_id() {
        return ack_id;
    }

    public void setAck_id(String ack_id) {
        this.ack_id = ack_id;
    }

    public LocalDateTime getReceivedTs() {
        return receivedTs;
    }

    public void setReceivedTs(LocalDateTime receivedTs) {
        this.receivedTs = receivedTs;
    }

    public LocalDateTime getSentTs() {
        return sentTs;
    }

    public void setSentTs(LocalDateTime sentTs) {
        this.sentTs = sentTs;
    }
}
