package com.assessment.consumer.microservice.repository;

import com.assessment.consumer.microservice.entity.SendMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SendMessageRepository extends JpaRepository<SendMessage, Long> {
    List<SendMessage> findByStatus(String status);
}

