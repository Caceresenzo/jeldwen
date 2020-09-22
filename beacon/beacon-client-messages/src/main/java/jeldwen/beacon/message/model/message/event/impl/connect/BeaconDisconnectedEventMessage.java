package jeldwen.beacon.message.model.message.event.impl.connect;

import jeldwen.beacon.message.model.message.event.BaseEventMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BeaconDisconnectedEventMessage extends BaseEventMessage {

	/* Constants */
	public static final String NAME = "beacon-disconnected";
	
	/* Variables */
	private String unique;
	
	/* Constructor */
	public BeaconDisconnectedEventMessage() {
		setName(NAME);
	}
	
}