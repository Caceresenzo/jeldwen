package jeldwen.backend.beacon.service;

import java.util.List;

import jeldwen.backend.beacon.entity.StopReason;
import jeldwen.backend.beacon.entity.StopReasonGroup;

public interface IStopReasonGroupService {

	List<StopReasonGroup> listAll();

	List<StopReasonGroup> list(long beaconId);

	List<StopReason> listReasons(long groupId);
	
}