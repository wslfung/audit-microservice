package com.wilsonfung.service.impl;

import com.wilsonfung.model.LogMessage;
import com.wilsonfung.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {
    
    private static final Logger log = LoggerFactory.getLogger(AuditLogServiceImpl.class);
    private final MongoTemplate mongoTemplate;

    @Override
    public void logMessage(LogMessage logMessage) {
        // Create query to check for existing document
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(logMessage.getUserId()))
              .addCriteria(Criteria.where("timestamp").is(logMessage.getTimestamp()))
              .addCriteria(Criteria.where("instance").is(logMessage.getInstance()))
              .addCriteria(Criteria.where("application").is(logMessage.getApplication()))
              .addCriteria(Criteria.where("message").is(logMessage.getMessage()));

        // Check if document already exists
        if (mongoTemplate.exists(query, LogMessage.class)) {
            log.info("Duplicate log message detected - not saving. Message: {}", logMessage.getMessage());
            return;
        }

        // Save new document
        mongoTemplate.save(logMessage, "log_messages");
        log.info("New log message saved successfully");
    }

    @Override
    public void archiveOldMessages(int daysOld) {
        try {
            // Calculate the cutoff timestamp (daysOld days ago)
            OffsetDateTime cutoffTime = OffsetDateTime.now(ZoneOffset.UTC)
                .minus(daysOld, ChronoUnit.DAYS);

            // Create query to find old messages
            Query query = new Query();
            query.addCriteria(Criteria.where("timestamp").lt(cutoffTime));

            // Count old messages
            long count = mongoTemplate.count(query, LogMessage.class, "log_messages");
            log.info("Found {} old messages to archive", count);

            if (count > 0) {
                // Find and archive old messages
                List<LogMessage> oldMessages = mongoTemplate.find(query, LogMessage.class, "log_messages");
                
                // Insert into archive collection
                for (LogMessage message : oldMessages) {
                    mongoTemplate.insert(message, "log_archive");
                }
                log.info("Successfully archived {} messages", oldMessages.size());

                // Delete from original collection
                mongoTemplate.remove(query, "log_messages");
                log.info("Successfully removed {} old messages from log_messages", oldMessages.size());
            }

        } catch (Exception e) {
            log.error("Error archiving old messages: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<LogMessage> retrieveLogMessages(boolean isAdmin, String userId, int count) {
        Query query = new Query();
        
        // If not admin, restrict to user's own messages
        if (!isAdmin) {
            query.addCriteria(Criteria.where("userId").is(userId));
        }
        
        // Sort by timestamp in descending order and limit results
        query.with(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "timestamp"));
        query.limit(count);
        
        return mongoTemplate.find(query, LogMessage.class, "log_messages");
    }
}
