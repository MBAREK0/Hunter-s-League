package com.mbarek0.web.huntersleague.repository;

import com.mbarek0.web.huntersleague.model.Competition;
import com.mbarek0.web.huntersleague.repository.dto.CompetitionRepoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, UUID> {
    Page<Competition> findByCodeContainingOrLocationContaining(String searchKeyword,String searchKeyword1, Pageable pageable);

    Optional<Competition> findByCode(String code);

    Optional<Competition> findByLocationAndDate(String location, LocalDateTime date);

    boolean existsByDateBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1);

    @Query("SELECT new com.mbarek0.web.huntersleague.repository.dto.CompetitionRepoDTO(" +
            "c.id, c.location, c.date, SIZE(c.participations)) " +
            "FROM Competition c " + "WHERE c.id = :id")
    CompetitionRepoDTO findByIdRepoDTO(@Param("id") UUID id);
}