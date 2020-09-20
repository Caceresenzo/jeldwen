package jeldwen.backend.beacon.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.backend.beacon.entity.StopReason;
import jeldwen.backend.beacon.entity.StopReasonGroup;
import jeldwen.backend.beacon.repository.BeaconRepository;
import jeldwen.backend.beacon.repository.StopReasonRepository;
import jeldwen.backend.beacon.service.IStopReasonService;

@Service
public class StopReasonServiceImpl implements IStopReasonService {
	
	@Autowired
	private StopReasonRepository stopReasonRepository;
	
	@Autowired
	private BeaconRepository beaconRepository;
	
	@Override
	public List<StopReason> listAll() {
		return stopReasonRepository.findAll();
	}
	
	@Override
	public List<StopReason> list(long beaconId, boolean expandGroups) {
		Optional<Beacon> optional = beaconRepository.findById(beaconId);
		
		if (optional.isPresent()) {
			Beacon beacon = optional.get();
			
			List<StopReason> stopReasons = new ArrayList<>();
			stopReasons.addAll(beacon.getStopReasons());
			
			if (expandGroups) {
				for (StopReasonGroup group : beacon.getStopReasonGroups()) {
					stopReasons.addAll(group.getChildren());
				}
			}
			
			return stopReasons;
		}
		
		return null;
	}
	
}