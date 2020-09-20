package jeldwen.backend.beacon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jeldwen.backend.beacon.entity.StopReasonGroup;

@Repository
public interface StopReasonGroupRepository extends JpaRepository<StopReasonGroup, Long> {
	
}