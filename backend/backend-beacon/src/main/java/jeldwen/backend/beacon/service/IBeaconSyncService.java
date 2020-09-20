package jeldwen.backend.beacon.service;

import jeldwen.backend.beacon.model.BeaconFamilyTaskContext;

public interface IBeaconSyncService {
	
	BeaconFamilyTaskContext start(long beaconId, long productFamilyId);
	
	BeaconFamilyTaskContext closeBecause(long beaconId, long reasonStopId);
	
}