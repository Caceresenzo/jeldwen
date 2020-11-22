package jeldwen.backend.beacon.repository.custom;

import java.time.LocalDate;
import java.util.List;

import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.backend.beacon.entity.ReportedStopReason;

public interface ReportedStopReasonRepositoryCustom {
	
	List<ReportedStopReason> search(Beacon beacon, LocalDate startDate, LocalDate endDate);
	
}