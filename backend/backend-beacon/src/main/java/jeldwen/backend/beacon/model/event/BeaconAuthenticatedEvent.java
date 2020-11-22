package jeldwen.backend.beacon.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BeaconAuthenticatedEvent {
	
	private final String unique;
	
}