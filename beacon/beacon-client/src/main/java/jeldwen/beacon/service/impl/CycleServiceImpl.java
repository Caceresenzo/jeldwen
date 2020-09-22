package jeldwen.beacon.service.impl;

import org.springframework.stereotype.Service;

import jeldwen.beacon.message.model.config.BeaconConfig;
import jeldwen.beacon.message.model.config.ProductFamilyConfig;
import jeldwen.beacon.message.model.config.StopReasonConfig;
import jeldwen.beacon.service.ICycleService;

@Service
public class CycleServiceImpl implements ICycleService {
	
	/* Variables */
	private BeaconConfig config;
	private boolean opened;
	private ProductFamilyConfig family;
	private StopReasonConfig currentStopReason;
	
	@Override
	public boolean open() {
		if (!opened && config != null) {
			return (opened = true);
		}
		
		return false;
	}
	
	@Override
	public boolean close() {
		if (opened) {
			return !(opened = false);
		}
		
		return false;
	}
	
	@Override
	public boolean getOpenState() {
		return opened;
	}
	
	@Override
	public boolean next() {
		if (!opened && config != null) {
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean useProductFamily(ProductFamilyConfig family) {
		if (opened && this.family != family) {
			this.family = family;
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean report(StopReasonConfig stopReason) {
		if (opened) {
			if (this.currentStopReason != null) {
			}
			
			this.currentStopReason = stopReason;
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public void setConfig(BeaconConfig config) {
		this.config = config;
	}
	
}