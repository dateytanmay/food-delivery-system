package com.assessment.consumer.microservice.service;

import com.assessment.consumer.microservice.entity.SendMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class KafkaConsumerService {
    private final KafkaConsumer<String, String> kafkaConsumer;
    private final InternalDBService internalDBService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaConsumerService(KafkaConsumer<String, String> kafkaConsumer, InternalDBService internalDBService) {
        this.kafkaConsumer = kafkaConsumer;
        this.internalDBService = internalDBService;
    }

    @PostConstruct
    public void startConsumer() {
        new Thread(() -> {
            kafkaConsumer.subscribe(Collections.singletonList("sms_topic"));

            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {
                    try {

                        JsonNode jsonNode = objectMapper.readTree(record.value());
                        String ack_id = jsonNode.get("ack_id").asText();
                        Long accountId = jsonNode.get("account_id").asLong();
                        String mobile = jsonNode.get("mobile").asText();
                        String message = jsonNode.get("message").asText();
                        String status = "NEW";
                        LocalDateTime receivedTs = LocalDateTime.now();
                        LocalDateTime origints = LocalDateTime.parse(jsonNode.get("origints").asText());

                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setAck_id(ack_id);
                        sendMessage.setAccountId(accountId);
                        sendMessage.setMobile(mobile);
                        sendMessage.setMessage(message);
                        sendMessage.setStatus(status);
                        sendMessage.setReceivedTs(receivedTs);
                        sendMessage.setOrigints(origints);

                        // Save to PostgreSQL
                        internalDBService.saveMessage(sendMessage);

                        System.out.println("Saved Message: " + sendMessage);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
