package com.assessment.demo.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TelecomOperatorController {

    @GetMapping("/telco/smsc")
    public ResponseEntity<String> sendSms(
            @RequestParam(name = "account_id") Long accountId,
            @RequestParam(name = "mobile") String mobile,
            @RequestParam(name = "message") String message) {

        if (accountId == null || mobile == null || message == null ||
                mobile.length() != 10 || message.length() > 160) {
            return ResponseEntity.badRequest().body("STATUS: REJECTED~~ RESPONSE_CODE: FAILURE");
        }

        String ackId = UUID.randomUUID().toString();

        String response = String.format("STATUS: ACCEPTED~~RESPONSE_CODE: SUCCESS~~%s", ackId);
        return ResponseEntity.ok(response);
    }
}


