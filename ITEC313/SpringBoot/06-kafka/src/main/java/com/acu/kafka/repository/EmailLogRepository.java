package com.acu.kafka.repository;

import com.acu.kafka.model.EmailLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmailLogRepository extends JpaRepository<EmailLogEntity, java.util.UUID> {
    
    List<EmailLogEntity> findByRecipientOrderBySentAtDesc(String recipient);
    
    List<EmailLogEntity> findByStatusOrderBySentAtDesc(EmailLogEntity.EmailStatus status);
    
    List<EmailLogEntity> findBySentAtBetweenOrderBySentAtDesc(LocalDateTime start, LocalDateTime end);
    
    List<EmailLogEntity> findByTemplateNameOrderBySentAtDesc(String templateName);
    
    Optional<EmailLogEntity> findTopByOrderBySentAtDesc();
}
