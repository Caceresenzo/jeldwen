package jeldwen.beacon.message.model.message.response.impl.workstation;

import java.util.List;

import jeldwen.beacon.message.model.config.BeaconConfig;
import jeldwen.beacon.message.model.descriptor.SimpleHourPerHourDescriptor;
import jeldwen.beacon.message.model.message.response.BaseResponseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WorkstationStateResponse extends BaseResponseMessage {
	
	/* Constants */
	public static final String NAME = "workstation-state";
	
	/* Variables */
	private boolean opened;
	private BeaconConfig beaconConfig;
	private Long activeFamilyId;
	private long seconds;
	private SimpleHourPerHourDescriptor currentHourPerHour;
	private List<SimpleHourPerHourDescriptor> hourPerHourHistory;
	
	/* Constructor */
	public WorkstationStateResponse() {
		setName(NAME);
	}
	
}