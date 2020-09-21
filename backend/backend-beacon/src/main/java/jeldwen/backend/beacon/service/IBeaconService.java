package jeldwen.backend.beacon.service;

import java.util.List;

import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.beacon.message.model.config.BeaconConfig;

public interface IBeaconService {
	
	List<Beacon> listAll();

	Beacon find(long id);

	boolean create(String unique);
	
	BeaconConfig getConfig(String unique);
	
}