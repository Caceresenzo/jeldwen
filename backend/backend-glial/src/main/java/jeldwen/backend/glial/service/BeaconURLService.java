package jeldwen.backend.glial.service;

import java.util.List;

import jeldwen.backend.glial.entity.BeaconURL;

public interface BeaconURLService {

	List<BeaconURL> all();
	
	List<BeaconURL> listMachines();
	
	BeaconURL set(String machine, String url);
	
	void delete(String machine);
	
}