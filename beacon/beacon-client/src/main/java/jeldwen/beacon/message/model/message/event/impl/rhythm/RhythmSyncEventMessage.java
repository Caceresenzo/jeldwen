package jeldwen.beacon.message.model.message.event.impl.rhythm;

import jeldwen.beacon.message.model.descriptor.SimpleHourPerHourDescriptor;
import jeldwen.beacon.message.model.message.event.BaseEventMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RhythmSyncEventMessage extends BaseEventMessage {

	/* Constants */
	public static final String NAME = "rhythm-sync";
	
	/* Variables */
	private long seconds;
	private SimpleHourPerHourDescriptor currentHourPerHour;
	
	/* Constructor */
	public RhythmSyncEventMessage() {
		setName(NAME);
	}
	
}