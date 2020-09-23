package jeldwen.backend.beacon.service;

import java.util.List;

import jeldwen.backend.beacon.dto.StopReasonUpdateBody;
import jeldwen.backend.beacon.entity.StopReason;
import jeldwen.backend.beacon.model.descriptor.SimpleStopReasonDescriptor;
import jeldwen.backend.beacon.service.base.IModelBasedService;

public interface IStopReasonService extends IModelBasedService<StopReason, SimpleStopReasonDescriptor, StopReasonUpdateBody> {
	
	List<SimpleStopReasonDescriptor> listAll(boolean ignoreGroup);
	
	List<StopReason> list(long beaconId, boolean expandGroups);
	
}