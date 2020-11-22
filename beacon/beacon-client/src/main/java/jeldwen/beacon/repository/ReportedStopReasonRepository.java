package jeldwen.beacon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jeldwen.beacon.entity.ReportedStopReason;

@Repository
public interface ReportedStopReasonRepository extends JpaRepository<ReportedStopReason, Long> {
	
	List<ReportedStopReason> findAllBySentFalse();
	
}