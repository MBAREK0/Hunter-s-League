package com.mbarek0.web.huntersleague.repository;

import com.mbarek0.web.huntersleague.model.Competition;
import com.mbarek0.web.huntersleague.model.Participation;
import com.mbarek0.web.huntersleague.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ParticipationRepository extends JpaRepository<Participation, UUID> {
 Boolean existsByUserAndCompetition(User userId, Competition competitionId);
 @Transactional
 @Modifying
 @Query(value = "UPDATE participations SET deleted = TRUE WHERE competition_id = :competitionId AND deleted = FALSE", nativeQuery = true)
 void updateDeletedFlagForParticipationByCompetitionId(@Param("competitionId") UUID competitionId);


 @Transactional
 @Modifying
 @Query(value = "UPDATE participations SET deleted = TRUE WHERE user_id = :userId AND deleted = FALSE", nativeQuery = true)
 void updateDeletedFlagForParticipationByUserId(@Param("userId") UUID userId);
}
