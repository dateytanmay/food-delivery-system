package com.assessment.demo.project.service;

import com.assessment.demo.project.entity.SendMessage;
import com.assessment.demo.project.repository.SendMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
class SmsProcessingService {
    @Autowired
    private SendMessageRepository sendMessageRepository;
    @Autowired
    private RestTemplate restTemplate;


    @Scheduled(fixedRate = 1000)
    public void processNewMessages() {
        List<SendMessage> newMessages = sendMessageRepository.findByStatus("NEW");

        for (SendMessage message : newMessages) {
            message.setStatus("INPROCESS");
            sendMessageRepository.save(message);

            String telcoUrl = String.format("http://localhost:8080/telco/smsc?account_id=%d&mobile=%s&message=%s",
                    message.getAccountId(), message.getMobile(), message.getMessage());

            String response = restTemplate.getForObject(telcoUrl, String.class);

            message.setTelcoResponse(response);
            message.setStatus("SENT");
            message.setSentTs(LocalDateTime.now());
            sendMessageRepository.save(message);
        }
    }
}