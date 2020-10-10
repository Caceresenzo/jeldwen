package jeldwen.beacon.service;

import jeldwen.beacon.message.model.config.BeaconConfig;
import jeldwen.beacon.message.model.config.ProductFamilyConfig;
import jeldwen.beacon.message.model.config.StopReasonConfig;

public interface ICycleService {
	
	boolean open();
	
	boolean close();
	
	boolean isUsable();
	
	boolean isOpen();
	
	boolean isClose();
	
	boolean next();
	
	boolean useProductFamily(ProductFamilyConfig family);
	
	boolean report(StopReasonConfig stopReason);
	
	boolean isStop();
	
	void setConfig(BeaconConfig config);
	
	void pulseCycle();
	
	void notifyState();
	
	void notifyState(boolean withHistory);

	void signal();
	
}