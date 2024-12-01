package com.mbarek0.web.huntersleague.repository;

import com.mbarek0.web.huntersleague.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByStatus(String status);
}
