package com.wilsonfung.config;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.wilsonfung.quartz.ArchiveLog;

@Configuration
public class QuartzConfig {
    
    @Value("${audit.log.archive.days-to-keep:30}")
    private String daysToKeep;
    
    @Bean
    public JobDetail archiveJobDetail() {
        return JobBuilder.newJob(ArchiveLog.class)
                .withIdentity("logRotateJob")
                .usingJobData("daysToKeep", daysToKeep)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger archiveJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInHours(24)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(archiveJobDetail())
                .withIdentity("archiveTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
