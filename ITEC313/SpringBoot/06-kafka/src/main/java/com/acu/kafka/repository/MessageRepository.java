package com.acu.kafka.repository;

import com.acu.kafka.model.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, java.util.UUID> {
    
    List<MessageEntity> findBySenderOrderByTimestampDesc(String sender);
    
    List<MessageEntity> findByMessageTypeOrderByTimestampDesc(MessageEntity.MessageType messageType);
    
    List<MessageEntity> findByTimestampBetweenOrderByTimestampDesc(LocalDateTime start, LocalDateTime end);
    
    List<MessageEntity> findByContentIn(List<String> contents);
    
    @Query("SELECT m FROM MessageEntity m ORDER BY m.timestamp DESC")
    List<MessageEntity> findRecentMessages();
    
    @Query("SELECT COUNT(m) FROM MessageEntity m WHERE m.timestamp >= :startOfDay AND m.timestamp < :endOfDay")
    Long countMessagesToday(@Param("startOfDay") java.time.LocalDateTime startOfDay, @Param("endOfDay") java.time.LocalDateTime endOfDay);
    
    @Query("SELECT m.messageType, COUNT(m) FROM MessageEntity m GROUP BY m.messageType")
    List<Object[]> countByMessageType();
}
