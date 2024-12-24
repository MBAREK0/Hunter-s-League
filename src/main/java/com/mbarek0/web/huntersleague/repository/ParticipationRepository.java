package com.mbarek0.web.huntersleague.repository;

import com.mbarek0.web.huntersleague.model.Competition;
import com.mbarek0.web.huntersleague.model.Participation;
import com.mbarek0.web.huntersleague.model.User;
import com.mbarek0.web.huntersleague.service.dto.PodiumDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
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


 @Transactional()
 @Query(value = "SELECT * FROM participations WHERE user_id = :userId AND deleted = FALSE", nativeQuery = true)
 Page<Participation> findByUserIdAndDeletedFalse(@Param("userId") UUID userId, Pageable pageable);


 @Transactional()
 @Query(value = """
    SELECT * 
    FROM participations 
    WHERE user_id = :userId 
      AND competition_id = :competitionId 
      AND deleted = FALSE
    """, nativeQuery = true)
 Optional<Participation> findByUserIdAndCompetitionId(
                         @Param("userId") UUID userId,
                         @Param("competitionId") UUID competitionId);


 @Query("SELECT new com.mbarek0.web.huntersleague.service.dto.PodiumDTO(p.user.username, p.score) " +
         "FROM Participation p " +
         "WHERE p.competition.id = :competitionId " +
         "ORDER BY p.score DESC LIMIT 3")
 List<PodiumDTO> findTopThreeByCompetition(@Param("competitionId") UUID competitionId);
}
