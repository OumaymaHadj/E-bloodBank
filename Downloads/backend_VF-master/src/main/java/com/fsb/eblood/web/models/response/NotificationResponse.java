package com.fsb.eblood.web.models.response;

import lombok.Data;

@Data
public class NotificationResponse {
    private int status;
    private String message;
    public NotificationResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
