package jeldwen.backend.beacon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jeldwen.backend.beacon.entity.StopReasonCategory;

@Repository
public interface StopReasonCategoryRepository extends JpaRepository<StopReasonCategory, Long> {
	
}