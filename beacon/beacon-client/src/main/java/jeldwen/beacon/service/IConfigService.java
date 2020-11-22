package jeldwen.beacon.service;

import java.net.SocketException;

import jeldwen.beacon.message.model.config.BeaconConfig;

public interface IConfigService {
	
	void store(BeaconConfig beaconConfig) throws Exception;
	
	BeaconConfig load() throws Exception;
	
	BeaconConfig current();
	
	String getUnique() throws SocketException;
	
}