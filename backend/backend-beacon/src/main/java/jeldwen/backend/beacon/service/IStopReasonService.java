package jeldwen.backend.beacon.service;

import java.util.List;

import jeldwen.backend.beacon.entity.StopReason;

public interface IStopReasonService {

	List<StopReason> listAll();
	
	List<StopReason> list(long beaconId, boolean expandGroups);
	
}