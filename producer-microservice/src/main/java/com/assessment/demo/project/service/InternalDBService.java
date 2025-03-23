package com.assessment.demo.project.service;

import com.assessment.demo.project.entity.SendMessage;
import com.assessment.demo.project.entity.User;
import com.assessment.demo.project.repository.SendMessageRepository;
import com.assessment.demo.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service

public class InternalDBService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SendMessageRepository sendMessageRepository;

    public Optional<Long> validateUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .map(User::getAccountId);
    }


    public SendMessage saveMessage(String ack_id, Long accountId, String mobile, String message, String status, String telcoResponse, LocalDateTime origints, LocalDateTime receivedTs) {
        SendMessage sendMessage = new SendMessage(ack_id, accountId, mobile, message, status, origints, telcoResponse, receivedTs);
        return sendMessageRepository.save(sendMessage);
    }

}

