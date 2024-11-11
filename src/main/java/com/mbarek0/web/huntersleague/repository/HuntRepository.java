package com.mbarek0.web.huntersleague.repository;

import com.mbarek0.web.huntersleague.model.Hunt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface HuntRepository extends JpaRepository<Hunt, UUID> {
}
