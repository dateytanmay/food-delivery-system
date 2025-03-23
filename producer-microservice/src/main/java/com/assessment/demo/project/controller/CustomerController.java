package com.assessment.demo.project.controller;

import com.assessment.demo.project.config.KafkaProducer;
import com.assessment.demo.project.service.InternalDBService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/telco")

public class CustomerController {
    private final InternalDBService internalDBService;
    private final KafkaProducer kafkaMessageProducer;

    public CustomerController(InternalDBService internalDBService, KafkaProducer kafkaMessageProducer) {
        this.internalDBService = internalDBService;
        this.kafkaMessageProducer = kafkaMessageProducer;
    }

    @GetMapping("/sendmsg")
    public ResponseEntity<String> sendMessage(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String mobile,
            @RequestParam String message) {

        if (mobile.length() != 10 || message.isEmpty() || message.length() > 160) {
            return ResponseEntity.badRequest().body("STATUS: REJECTED~~RESPONSE_CODE: FAILURE");
        }

        Optional<Long> accountIdOpt = internalDBService.validateUser(username, password);

        if (accountIdOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("STATUS: REJECTED~~RESPONSE_CODE: FAILURE");
        }

        Long accountId = accountIdOpt.get();
        String ackId = UUID.randomUUID().toString();

        String jsonMessage = String.format(
                "{\"ack_id\":\"%s\",\"account_id\":%d,\"mobile\":\"%s\",\"message\":\"%s\",\"origints\":\"%s\"}",
                ackId, accountId, mobile, message, LocalDateTime.now()
        );

        System.out.println(jsonMessage);

        kafkaMessageProducer.sendMessage("sms_topic", jsonMessage);


        return ResponseEntity.ok("STATUS: ACCEPTED~~RESPONSE_CODE: SUCCESS~~" + ackId);
    }
}
