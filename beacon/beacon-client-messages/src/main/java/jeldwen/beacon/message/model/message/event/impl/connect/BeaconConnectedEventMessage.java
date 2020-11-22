package jeldwen.beacon.message.model.message.event.impl.connect;

import jeldwen.beacon.message.model.message.event.BaseEventMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BeaconConnectedEventMessage extends BaseEventMessage {

	/* Constants */
	public static final String NAME = "beacon-connected";
	
	/* Variables */
	private String unique;
	
	/* Constructor */
	public BeaconConnectedEventMessage() {
		setName(NAME);
	}
	
}