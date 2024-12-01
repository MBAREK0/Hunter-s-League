package com.mbarek0.web.huntersleague.repository;

import com.mbarek0.web.huntersleague.model.Hunt;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface HuntRepository extends JpaRepository<Hunt, UUID> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE hunts SET deleted = TRUE WHERE participation_id IN (SELECT p.id FROM participations p WHERE p.competition_id = :competitionId)", nativeQuery = true)
    void updateDeletedFlagForHuntsByCompetitionId(@Param("competitionId") UUID competitionId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE hunts SET deleted = TRUE WHERE participation_id IN (SELECT p.id FROM participations p WHERE p.user_id = :userId)", nativeQuery = true)
    void updateDeletedFlagForHuntsByUserId(@Param("userId") UUID userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE hunts SET deleted = TRUE WHERE participation_id = :participationId", nativeQuery = true)
    void updateDeletedFlagForHuntsByParticipationId(@Param("participationId") UUID participationId);

}
