package jeldwen.backend.beacon.service;

import jeldwen.backend.beacon.dto.BeaconUpdateBody;
import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.backend.beacon.model.descriptor.SimpleBeaconDescriptor;
import jeldwen.backend.beacon.service.base.IModelBasedService;
import jeldwen.beacon.message.model.config.BeaconConfig;

public interface IBeaconService extends IModelBasedService<Beacon, SimpleBeaconDescriptor, BeaconUpdateBody> {
	
	boolean create(String unique);
	
	BeaconConfig getConfig(String unique);
	
	Boolean reconfigure(long id);
	
	Boolean forceTrigger(long id);
	
}