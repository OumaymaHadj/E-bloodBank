package com.fsb.eblood.web.controller;


import com.fsb.eblood.dao.entities.FCMToken;
import com.fsb.eblood.service.fcm.FCMService;
import com.fsb.eblood.service.fcm.PushNotificationService;
import com.fsb.eblood.web.models.request.NotificationRequest;
import com.fsb.eblood.web.models.response.NotificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fcm")
public class FCMController {
    @Autowired
    FCMService fcmService;


    @Autowired
    PushNotificationService pushNotificationService;

    @PostMapping("/addToken")
    public void createToken(@RequestBody FCMToken token) {
        fcmService.addNewToken(token);
    }

    @GetMapping("/getToken/{username}")
    public List<FCMToken> getToken(@PathVariable ("username") String username){ return fcmService.getToken(username);}


    @PostMapping("/send-notification")
    public ResponseEntity sendTokenNotification(@RequestBody NotificationRequest request) {
        pushNotificationService.sendPushNotification(request);
        System.out.println("yes");
        return new ResponseEntity<>(new NotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);

    }

    @PostMapping("/notification/data")
    public ResponseEntity sendDataNotification(@RequestBody NotificationRequest request) {
        pushNotificationService.sendPushNotificationTopic(request);
        return new ResponseEntity<>(new NotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

}
