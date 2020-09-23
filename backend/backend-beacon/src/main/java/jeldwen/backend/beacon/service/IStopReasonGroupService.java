package jeldwen.backend.beacon.service;

import java.util.List;

import jeldwen.backend.beacon.dto.StopReasonGroupUpdateBody;
import jeldwen.backend.beacon.entity.StopReason;
import jeldwen.backend.beacon.entity.StopReasonGroup;
import jeldwen.backend.beacon.model.descriptor.SimpleStopReasonGroupDescriptor;
import jeldwen.backend.beacon.service.base.IModelBasedService;

public interface IStopReasonGroupService extends IModelBasedService<StopReasonGroup, SimpleStopReasonGroupDescriptor, StopReasonGroupUpdateBody> {

	List<StopReason> listReasons(long groupId);
	
}