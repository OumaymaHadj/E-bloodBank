package com.fsb.eblood.web.models.request;


import lombok.Data;

@Data
public class NotificationRequest {

    private String title;
    private String message;
    private String topic;
    private String token;
}