package jeldwen.beacon.service;

import jeldwen.beacon.entity.ReportedStopReason;
import jeldwen.beacon.message.model.config.ProductFamilyConfig;
import jeldwen.beacon.message.model.config.StopReasonConfig;

public interface IStopReasonService {
	
	void reportNow(String message, ProductFamilyConfig productFamily, long duration);
	
	void reportNow(StopReasonConfig stopReason, ProductFamilyConfig productFamily, long duration);
	
	void sendReportedStopReason(ReportedStopReason reportedStopReason);
	
	void flush();
	
}