package com.mbarek0.web.huntersleague.repository;

import com.mbarek0.web.huntersleague.model.Competition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, UUID> {
    Page<Competition> findByCodeContainingOrLocationContaining(String searchKeyword,String searchKeyword1, Pageable pageable);

    Optional<Competition> findByCode(String code);

    Optional<Competition> findByLocationAndDate(String location, LocalDateTime date);
}