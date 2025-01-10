package com.mbarek0.web.huntersleague.scheduler;

import com.mbarek0.web.huntersleague.repository.CompetitionRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@AllArgsConstructor
@Component
public class ManageRegistration {
    private final CompetitionRepository competitionRepository;

    @Scheduled(cron = "* */5 * * * ?")
    public void manageTrueRegistration(){
        competitionRepository.getAllByOpenRegistrationTrueAndDeletedFalse()
        .forEach(r ->  {
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(r.getDate())){
                 r.setOpenRegistration(false);
                competitionRepository.save(r);
            }
        });

    }

    @Scheduled(cron = "* */5 * * * ?")
    public void manageFalseRegistration(){
        competitionRepository.getAllByOpenRegistrationFalseAndDeletedFalse()
                .forEach(r ->  {
                    LocalDateTime now = LocalDateTime.now();
                    if (now.isBefore(r.getDate())){
                        r.setOpenRegistration(true);
                        competitionRepository.save(r);
                    }
                });

    }
}
