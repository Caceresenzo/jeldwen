package jeldwen.backend.beacon.model.descriptor;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SimpleReportedStopReasonDescriptor {
	
	private long id;
	private long beaconId;
	private Long stopReasonId;
	private String message;
	private Long productFamilyId;
	private long duration;
	
	public boolean isCustom() {
		return message != null;
	}
	
}