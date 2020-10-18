package jeldwen.backend.beacon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jeldwen.backend.beacon.entity.ReportedStopReason;
import jeldwen.backend.beacon.repository.custom.ReportedStopReasonRepositoryCustom;

@Repository
public interface ReportedStopReasonRepository extends JpaRepository<ReportedStopReason, Long>, ReportedStopReasonRepositoryCustom {

	
}