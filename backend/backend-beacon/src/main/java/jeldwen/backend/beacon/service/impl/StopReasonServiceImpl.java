package jeldwen.backend.beacon.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.backend.beacon.entity.StopReason;
import jeldwen.backend.beacon.entity.StopReasonGroup;
import jeldwen.backend.beacon.model.descriptor.SimpleStopReasonDescriptor;
import jeldwen.backend.beacon.repository.BeaconRepository;
import jeldwen.backend.beacon.repository.StopReasonRepository;
import jeldwen.backend.beacon.service.IStopReasonService;
import jeldwen.backend.common.util.TypeAwareMapper;

@Service
public class StopReasonServiceImpl implements IStopReasonService {
	
	@Autowired
	private StopReasonRepository stopReasonRepository;
	
	@Autowired
	private BeaconRepository beaconRepository;
	
	/* Mappers */
	private final TypeAwareMapper<StopReason, SimpleStopReasonDescriptor> simpleStopReasonDescriptorMapper;
	
	public StopReasonServiceImpl() {
		this.simpleStopReasonDescriptorMapper = new TypeAwareMapper<StopReason, SimpleStopReasonDescriptor>() {
		};
	}
	
	@Override
	public List<SimpleStopReasonDescriptor> listAll(boolean ignoreGroup) {
		return simpleStopReasonDescriptorMapper.toDtos(ignoreGroup ? stopReasonRepository.findAll() : stopReasonRepository.findAllByAttachedIsFalse());
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
	
	@Override
	public List<StopReason> listAllByIds(List<Long> ids) {
		return stopReasonRepository.findAllById(ids);
	}
	
}