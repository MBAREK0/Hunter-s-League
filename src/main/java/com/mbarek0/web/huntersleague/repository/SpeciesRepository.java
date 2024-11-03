package com.mbarek0.web.huntersleague.repository;

import com.mbarek0.web.huntersleague.model.Species;
import com.mbarek0.web.huntersleague.model.enums.SpeciesType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface SpeciesRepository extends JpaRepository<Species, UUID> {


    Page<Species> findByCategory(SpeciesType category, Pageable pageable);

}
