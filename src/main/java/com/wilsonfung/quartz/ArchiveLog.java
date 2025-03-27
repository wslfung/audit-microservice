package com.wilsonfung.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wilsonfung.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArchiveLog implements Job {
    private static final Logger logger = LoggerFactory.getLogger(ArchiveLog.class);
    
    @Autowired
    private AuditLogService auditLogService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            // Get the configurable number of days from job data or use default
            String daysToKeepStr = context.getJobDetail()
                    .getJobDataMap()
                    .getString("daysToKeep");
            
            int daysToKeep;
            try {
                daysToKeep = Integer.parseInt(daysToKeepStr);
                if (daysToKeep < 1) {
                    throw new IllegalArgumentException("daysToKeep must be greater than 0");
                }
            } catch (NumberFormatException e) {
                logger.warn("Invalid daysToKeep value: {}. Using default of 30 days.", daysToKeepStr);
                daysToKeep = 30;
            }

            logger.info("Starting log archive process. Keeping logs for {} days", daysToKeep);
            auditLogService.archiveOldMessages(daysToKeep);
            logger.info("Log archive process completed successfully");
        } catch (Exception e) {
            logger.error("Error during log archive process: {}", e.getMessage(), e);
            throw new JobExecutionException(e);
        }
    }
}
