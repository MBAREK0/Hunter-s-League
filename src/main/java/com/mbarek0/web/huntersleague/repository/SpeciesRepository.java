package com.mbarek0.web.huntersleague.repository;

import com.mbarek0.web.huntersleague.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpeciesRepository extends JpaRepository<Species, UUID> {

}
