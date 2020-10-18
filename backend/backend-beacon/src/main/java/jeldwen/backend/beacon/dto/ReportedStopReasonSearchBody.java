package jeldwen.backend.beacon.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReportedStopReasonSearchBody {
	
	public static final ReportedStopReasonSearchBody EMPTY = new ReportedStopReasonSearchBody();
	
	private LocalDate startDate;
	private LocalDate endDate;
	
}