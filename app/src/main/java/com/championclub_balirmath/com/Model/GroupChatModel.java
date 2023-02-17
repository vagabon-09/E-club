package com.championclub_balirmath.com.Model;

public class GroupChatModel {
    private String message, userId, name;
    private long timestamp;

    public GroupChatModel() {
    }

    public GroupChatModel(String message, String userId, String name, long timestamp) {
        this.message = message;
        this.userId = userId;
        this.name = name;
        this.timestamp = timestamp;
    }

    public GroupChatModel(String message, String userId, String name) {
        this.message = message;
        this.userId = userId;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
