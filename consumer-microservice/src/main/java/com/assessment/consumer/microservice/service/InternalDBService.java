package com.assessment.consumer.microservice.service;

import com.assessment.consumer.microservice.entity.SendMessage;
import com.assessment.consumer.microservice.repository.SendMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class InternalDBService {
    @Autowired
    private SendMessageRepository sendMessageRepository;

    public InternalDBService(SendMessageRepository sendMessageRepository) {
        this.sendMessageRepository = sendMessageRepository;
    }

    public void saveMessage(SendMessage message) {
       sendMessageRepository.save(message);
    }
}
