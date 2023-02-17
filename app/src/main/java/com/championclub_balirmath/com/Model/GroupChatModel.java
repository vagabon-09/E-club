package com.championclub_balirmath.com.Model;

public class GroupChatModel {
    private String message, userId;
    private long timestamp;

    public GroupChatModel() {
    }

    public GroupChatModel(String message, String userId, long timestamp) {
        this.message = message;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    public GroupChatModel(String message, String userId) {
        this.message = message;
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
