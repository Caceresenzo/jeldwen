package jeldwen.backend.beacon.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.backend.beacon.entity.StopReason;
import jeldwen.backend.beacon.entity.StopReasonGroup;
import jeldwen.backend.beacon.repository.BeaconRepository;
import jeldwen.backend.beacon.repository.StopReasonGroupRepository;
import jeldwen.backend.beacon.service.IStopReasonGroupService;

@Service
public class StopReasonGroupServiceImpl implements IStopReasonGroupService {
	
	@Autowired
	private StopReasonGroupRepository stopReasonGroupRepository;
	
	@Autowired
	private BeaconRepository beaconRepository;
	
	@Override
	public List<StopReasonGroup> listAll() {
		return stopReasonGroupRepository.findAll();
	}
	
	@Override
	public List<StopReasonGroup> list(long beaconId) {
		Optional<Beacon> optional = beaconRepository.findById(beaconId);
		
		if (optional.isPresent()) {
			return optional.get().getStopReasonGroups();
		}
		
		return null;
	}
	
	@Override
	public List<StopReason> listReasons(long groupId) {
		Optional<StopReasonGroup> optional = stopReasonGroupRepository.findById(groupId);
		
		if (optional.isPresent()) {
			return optional.get().getChildren();
		}
		
		return null;
	}
	
}