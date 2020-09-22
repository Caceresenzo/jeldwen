package jeldwen.backend.beacon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jeldwen.backend.beacon.entity.StopReason;

@Repository
public interface StopReasonRepository extends JpaRepository<StopReason, Long> {
	
	List<StopReason> findByIdIn(List<Long> ids);
	
	List<StopReason> findAllByAttachedIsFalse();
	
}