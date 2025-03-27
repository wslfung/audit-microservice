package com.wilsonfung.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import com.wilsonfung.model.LogMessage;
import com.wilsonfung.service.AuditLogService;
import java.util.Date;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaConsumer {
    
    private final AuditLogService auditLogService;
    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaConsumer(AuditLogService auditLogService, ObjectMapper objectMapper) {
        this.auditLogService = auditLogService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "changelog")
    public void listen(String message, @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        try {
            log.info("Received message: {}", message);
            
            // Parse JSON message into Map
            Map<String, Object> messageMap = objectMapper.readValue(message, new TypeReference<Map<String, Object>>() {});
            
            // Create LogMessage from the parsed data
            LogMessage logMessage = new LogMessage();
            logMessage.setMessage((String) messageMap.get("message"));
            logMessage.setTimestamp(new Date(timestamp));
            logMessage.setInstance((String) messageMap.get("instance"));
            logMessage.setApplication((String) messageMap.get("application"));
            logMessage.setAdditionalInformation((String) messageMap.get("additionalInformation"));
            logMessage.setUserId((String) messageMap.get("userId"));
            
            // Generate signature
            logMessage.generateSignature();
            
            // Save to MongoDB
            auditLogService.logMessage(logMessage);
            
            log.info("Successfully processed and logged message with timestamp: {}", logMessage.getTimestamp());
            
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage(), e);
            // Consider adding error handling logic here
        }
    }
}
