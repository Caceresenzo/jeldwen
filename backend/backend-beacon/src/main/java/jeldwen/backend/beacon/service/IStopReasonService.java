package jeldwen.backend.beacon.service;

import java.util.List;

import jeldwen.backend.beacon.entity.StopReason;
import jeldwen.backend.beacon.model.descriptor.SimpleStopReasonDescriptor;

public interface IStopReasonService {

	List<SimpleStopReasonDescriptor> listAll(boolean ignoreGroup);
	
	List<StopReason> list(long beaconId, boolean expandGroups);
	
	List<StopReason> listAllByIds(List<Long> ids);
	
}