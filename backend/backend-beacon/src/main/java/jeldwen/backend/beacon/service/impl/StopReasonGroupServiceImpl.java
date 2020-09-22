package jeldwen.backend.beacon.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.backend.beacon.entity.StopReason;
import jeldwen.backend.beacon.entity.StopReasonGroup;
import jeldwen.backend.beacon.model.descriptor.SimpleStopReasonGroupDescriptor;
import jeldwen.backend.beacon.repository.BeaconRepository;
import jeldwen.backend.beacon.repository.StopReasonGroupRepository;
import jeldwen.backend.beacon.service.IStopReasonGroupService;
import jeldwen.backend.common.util.TypeAwareMapper;

@Service
public class StopReasonGroupServiceImpl implements IStopReasonGroupService {
	
	@Autowired
	private StopReasonGroupRepository stopReasonGroupRepository;
	
	@Autowired
	private BeaconRepository beaconRepository;
	
	/* Mappers */
	private final TypeAwareMapper<StopReasonGroup, SimpleStopReasonGroupDescriptor> simpleStopReasonGroupDescriptorMapper;
	
	public StopReasonGroupServiceImpl() {
		this.simpleStopReasonGroupDescriptorMapper = new TypeAwareMapper<StopReasonGroup, SimpleStopReasonGroupDescriptor>() {
		};
	}
	
	@Override
	public List<SimpleStopReasonGroupDescriptor> listAll() {
		return simpleStopReasonGroupDescriptorMapper.toDtos(stopReasonGroupRepository.findAll());
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
	
	@Override
	public List<StopReasonGroup> listAllByIds(List<Long> ids) {
		return stopReasonGroupRepository.findAllById(ids);
	}
	
}