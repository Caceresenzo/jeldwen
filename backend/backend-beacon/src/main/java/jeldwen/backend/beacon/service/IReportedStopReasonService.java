package jeldwen.backend.beacon.service;

import jeldwen.backend.beacon.dto.ReportedStopReasonSearchBody;
import jeldwen.backend.beacon.dto.ReportedStopReasonSearchResult;
import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.beacon.message.model.message.request.impl.report.StopReasonReportRequest;

public interface IReportedStopReasonService {

	void store(Beacon beacon, StopReasonReportRequest request);
	
	ReportedStopReasonSearchResult search(Long beaconId, ReportedStopReasonSearchBody body);
	
}