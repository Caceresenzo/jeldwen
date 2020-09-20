package jeldwen.backend.beacon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jeldwen.backend.beacon.entity.StopReason;

@Repository
public interface StopReasonRepository extends JpaRepository<StopReason, Long> {
	
}