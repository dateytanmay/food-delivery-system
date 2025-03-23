package com.assessment.demo.project.controller;


import com.assessment.demo.project.entity.SendMessage;
import com.assessment.demo.project.service.InternalDBService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/internal-db")
public class InternalDBController {
    private final InternalDBService internalDBService;

    public InternalDBController(InternalDBService internalDBService) {
        this.internalDBService = internalDBService;
    }

    @PostMapping("/sendmsg")
    public ResponseEntity<SendMessage> sendMessage(
            @RequestParam Long accountId,
            @RequestParam String mobile,
            @RequestParam String message,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String telcoResponse,
            @RequestParam(required = false) LocalDateTime origints,
            @RequestParam(required = false) LocalDateTime receivedTs) {
        String ack_id = UUID.randomUUID().toString();
        SendMessage savedMessage = internalDBService.saveMessage(
                ack_id, accountId, mobile, message,
                status != null ? status : "PENDING",
                telcoResponse != null ? telcoResponse : "N/A",
                origints != null ? origints : LocalDateTime.now(),
                receivedTs != null ? receivedTs : LocalDateTime.now()
        );

        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/getAccountId")
    public ResponseEntity<?> getAccountId(
            @RequestParam String username,
            @RequestParam String password) {

        Optional<Long> accountId = internalDBService.validateUser(username, password);

        return accountId.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(401).body(Long.valueOf("Invalid credentials")));
    }
}

