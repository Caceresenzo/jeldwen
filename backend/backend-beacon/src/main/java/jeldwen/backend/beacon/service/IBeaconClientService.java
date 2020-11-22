package jeldwen.backend.beacon.service;

import java.util.List;

public interface IBeaconClientService {
	
	List<String> listConnectedUniques();

	boolean reconfigure(String unique);

	boolean forceTrigger(String unique);
	
}