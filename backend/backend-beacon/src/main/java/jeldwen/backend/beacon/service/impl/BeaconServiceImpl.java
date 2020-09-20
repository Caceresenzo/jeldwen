package jeldwen.backend.beacon.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.backend.beacon.repository.BeaconRepository;
import jeldwen.backend.beacon.service.IBeaconService;

@Service
public class BeaconServiceImpl implements IBeaconService {
	
	@Autowired
	private BeaconRepository beaconRepository;
	
	@Override
	public List<Beacon> listAll() {
		return beaconRepository.findAll();
	}
	
	@Override
	public Beacon find(long id) {
		return beaconRepository.findById(id).orElse(null);
	}
	
}