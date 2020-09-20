package jeldwen.backend.beacon.service;

import java.util.List;

import jeldwen.backend.beacon.entity.Beacon;

public interface IBeaconService {
	
	List<Beacon> listAll();

	Beacon find(long id);
	
}