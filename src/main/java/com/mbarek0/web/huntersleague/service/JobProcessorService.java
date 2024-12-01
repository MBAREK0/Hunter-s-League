package com.mbarek0.web.huntersleague.service;



import com.mbarek0.web.huntersleague.model.Job;
import com.mbarek0.web.huntersleague.repository.JobRepository;
import com.mbarek0.web.huntersleague.repository.ParticipationRepository;
import com.mbarek0.web.huntersleague.repository.HuntRepository;
import com.mbarek0.web.huntersleague.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JobProcessorService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private HuntRepository huntRepository;

    @Autowired
    private CompetitionRepository competitionRepository;

    // Method to create a new job for cascading delete
    public void createCascadeDeleteJob(UUID competitionId, String jobType) {
        Job job = Job.builder()
                .jobType(jobType)
                .status("PENDING") // Set status to PENDING
                .payload(competitionId) // Store the competitionId in payload
                .build();
        jobRepository.save(job); // Insert the job into the job table
    }

    // Method to process the job
    @Transactional
    public void processJob(Long jobId) {
        Optional<Job> jobOpt = jobRepository.findById(jobId);
        if (jobOpt.isPresent()) {
            Job job = jobOpt.get();
            if ("PENDING".equals(job.getStatus())) {
                UUID competitionId = job.getPayload();
                String jobType = job.getJobType();
                if ("USER".equals(jobType)) {
                    cascadeDeleteForUser(competitionId); // Execute the cascading delete
                } else if ("COMPETITION".equals(jobType)) {
                    cascadeDeleteForCompetition(competitionId); // Execute the cascading delete
                } else if ("PARTICIPATION".equals(jobType)) {
                    cascadeDeleteForParticipation(competitionId); // Execute the cascading delete
                }

                // Mark the job as processed
                job.setStatus("PROCESSED");
                jobRepository.save(job); // Save the processed job
            }
        }
    }

    // Method for user cascading delete
     @Transactional
     protected  void cascadeDeleteForUser(UUID userId) {

        // Update competitions
        competitionRepository.updateDeletedFlagForCompetitionsByUserId(userId);

        // Update participations
        participationRepository.updateDeletedFlagForParticipationByUserId(userId);

        // Update hunts (cascade delete based on participation)
        huntRepository.updateDeletedFlagForHuntsByUserId(userId);
     }

    // Method for competition cascading delete
    @Transactional
    protected void cascadeDeleteForCompetition(UUID competitionId) {
        // Update participations
        participationRepository.updateDeletedFlagForParticipationByCompetitionId(competitionId);

        // Update hunts (cascade delete based on participation)
        huntRepository.updateDeletedFlagForHuntsByCompetitionId(competitionId);
    }


    // Method for participation cascading delete
    @Transactional
    protected void cascadeDeleteForParticipation(UUID participationId) {
        // Update hunts
        huntRepository.updateDeletedFlagForHuntsByParticipationId(participationId);
    }


    // Optional: Method to process all unprocessed jobs (to be called periodically)
    public void processPendingJobs() {
        List<Job> pendingJobs = jobRepository.findByStatus("PENDING");
        for (Job job : pendingJobs) {
            processJob(job.getId());
        }
    }
}
