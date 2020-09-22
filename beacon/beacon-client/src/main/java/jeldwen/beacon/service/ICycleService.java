package jeldwen.beacon.service;

import jeldwen.beacon.message.model.config.BeaconConfig;
import jeldwen.beacon.message.model.config.ProductFamilyConfig;
import jeldwen.beacon.message.model.config.StopReasonConfig;

public interface ICycleService {
	
	boolean open();
	
	boolean close();
	
	boolean getOpenState();
	
	boolean next();
	
	boolean useProductFamily(ProductFamilyConfig family);
	
	boolean report(StopReasonConfig stopReason);
	
	void setConfig(BeaconConfig config);
	
}