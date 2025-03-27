package com.wilsonfung.service;

import com.wilsonfung.model.LogMessage;
import java.util.List;

public interface AuditLogService {
    void logMessage(LogMessage logMessage);
    void archiveOldMessages(int daysOld);
    List<LogMessage> retrieveLogMessages(boolean isAdmin, String userId, int count);
}
