package jeldwen.backend.beacon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jeldwen.backend.beacon.entity.StopReasonSummary;

@Repository
public interface StopReasonSummaryRepository extends JpaRepository<StopReasonSummary, Long> {
	
}