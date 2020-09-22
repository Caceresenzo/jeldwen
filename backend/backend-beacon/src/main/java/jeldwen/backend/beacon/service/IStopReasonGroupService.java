package jeldwen.backend.beacon.service;

import java.util.List;

import jeldwen.backend.beacon.entity.StopReason;
import jeldwen.backend.beacon.entity.StopReasonGroup;
import jeldwen.backend.beacon.model.descriptor.SimpleStopReasonGroupDescriptor;

public interface IStopReasonGroupService {

	List<SimpleStopReasonGroupDescriptor> listAll();

	List<StopReasonGroup> list(long beaconId);

	List<StopReason> listReasons(long groupId);
	
	List<StopReasonGroup> listAllByIds(List<Long> ids);
	
}