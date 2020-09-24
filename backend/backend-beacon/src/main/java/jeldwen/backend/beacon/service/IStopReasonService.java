package jeldwen.backend.beacon.service;

import java.util.List;
import java.util.Set;

import jeldwen.backend.beacon.dto.StopReasonUpdateBody;
import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.backend.beacon.entity.StopReason;
import jeldwen.backend.beacon.entity.StopReasonGroup;
import jeldwen.backend.beacon.model.descriptor.SimpleStopReasonDescriptor;
import jeldwen.backend.beacon.service.base.IModelBasedService;

public interface IStopReasonService extends IModelBasedService<StopReason, SimpleStopReasonDescriptor, StopReasonUpdateBody> {
	
	List<SimpleStopReasonDescriptor> listAll(boolean includeFree, Inclusion includeParent, Long parentId, boolean ignoreParent);
	
	List<StopReason> list(long beaconId, boolean expandGroups);

	StopReason ungroup(long id);

	@Override
	StopReason delete(long id);

	Set<StopReason> saveAsParent(List<StopReason> stopReasons, StopReasonGroup group, CollisionResolution ifCollision);
	
	Set<StopReason> saveAsParent(List<StopReason> stopReasons, Beacon beacon, CollisionResolution ifCollision);
	
	public enum CollisionResolution {
		
		UNGROUP_BEFORE, IGNORE
		
	}
	
	public enum Inclusion {
		
		BEACON, GROUP
		
	}
	
}