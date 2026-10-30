
package com.example.insuranceproposal.dto;

import java.util.Map;

public class ApiErrorResponse {

    private int status;
    private String error;
    private String message;
    private Map<String, String> messages;

    public ApiErrorResponse() {
    }

    public ApiErrorResponse(
            int status,
            String error,
            String message,
            Map<String, String> messages) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.messages = messages;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, String> messages) {
        this.messages = messages;
    }
}
