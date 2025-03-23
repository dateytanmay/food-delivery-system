package com.assessment.demo.project.repository;

import com.assessment.demo.project.entity.SendMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SendMessageRepository extends JpaRepository<SendMessage, Long> {
    List<SendMessage> findByStatus(String status);
}

