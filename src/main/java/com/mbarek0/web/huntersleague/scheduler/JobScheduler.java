package com.mbarek0.web.huntersleague.scheduler;


import com.mbarek0.web.huntersleague.service.JobProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobScheduler {

    @Autowired
    private JobProcessorService jobProcessorService;

    @Scheduled(cron = "*/10 * * * * ?")
    public void processJobs() {
        jobProcessorService.processPendingJobs();
    }
}
