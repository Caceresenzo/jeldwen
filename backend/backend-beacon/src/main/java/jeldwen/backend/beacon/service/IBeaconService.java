package jeldwen.backend.beacon.service;

import java.util.List;

import jeldwen.backend.beacon.dto.BeaconUpdateBody;
import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.backend.beacon.model.descriptor.SimpleBeaconDescriptor;
import jeldwen.beacon.message.model.config.BeaconConfig;

public interface IBeaconService {
	
	List<SimpleBeaconDescriptor> listAll();

	Beacon find(long id);

	boolean create(String unique);
	
	BeaconConfig getConfig(String unique);

	Beacon update(long id, BeaconUpdateBody body);
	
}