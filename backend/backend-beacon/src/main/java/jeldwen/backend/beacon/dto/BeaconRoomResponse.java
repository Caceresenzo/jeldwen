package jeldwen.backend.beacon.dto;

import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.backend.beacon.model.BeaconFamilyTaskContext;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BeaconRoomResponse {
	
	private Beacon beacon;
	private BeaconFamilyTaskContext taskContext;
	
}