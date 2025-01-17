package com.mbarek0.web.huntersleague.repository;

import com.mbarek0.web.huntersleague.model.Competition;
import com.mbarek0.web.huntersleague.repository.dto.CompetitionRepoDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, UUID> {
    Page<Competition> findAllByDeletedFalse(Pageable pageable);
    @Query(value = "SELECT * FROM competitions c WHERE c.deleted = false AND (LOWER(c.code) LIKE %:searchKeyword% OR LOWER(c.location) LIKE %:searchKeyword1%)",
            nativeQuery = true)
    Page<Competition> searchCompetitions(@Param("searchKeyword") String searchKeyword,
                                         @Param("searchKeyword1") String searchKeyword1,
                                         Pageable pageable);    Optional<Competition> findByCodeAndDeletedFalse(String code);
    Optional<Competition> findByLocationAndDateAndDeletedFalse(String location, LocalDateTime date);
    boolean existsByDateBetweenAndDeletedFalse(LocalDateTime localDateTime, LocalDateTime localDateTime1);
    int countByDateBetweenAndDeletedFalse(LocalDateTime localDateTime, LocalDateTime localDateTime1);
    @Query("SELECT new com.mbarek0.web.huntersleague.repository.dto.CompetitionRepoDTO(" +
            "c.id, c.location, c.date, SIZE(c.participations)) " +
            "FROM Competition c WHERE c.id = :id AND c.deleted = false")
    CompetitionRepoDTO findByIdRepoDTO(@Param("id") UUID id);
    List<Competition> getAllByOpenRegistrationTrueAndDeletedFalse();
    List<Competition> getAllByOpenRegistrationFalseAndDeletedFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Competition c SET c.deleted = true WHERE c.id = :id")
    void softDeleteById(@Param("id") UUID id);


    @Modifying
    @Transactional
    @Query(value = "UPDATE competitions SET deleted = TRUE WHERE id IN (SELECT p.competition_id FROM participations p WHERE p.user_id = :userId)", nativeQuery = true)
    void updateDeletedFlagForCompetitionsByUserId(@Param("userId") UUID userId);
}