package com.experis.pizza.model;

public class AlertMessage {
    public enum AlertMessagesType {
        SUCCESS, ERROR
    }

    private AlertMessagesType type;
    private String message;

    public AlertMessage(AlertMessagesType type, String message) {
        this.type = type;
        this.message = message;
    }

    public AlertMessage() {
    }

    public AlertMessagesType getType() {
        return type;
    }

    public void setType(AlertMessagesType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AlertMessage{" +
                "type=" + type +
                ", message='" + message + '\'' +
                '}';
    }
}
