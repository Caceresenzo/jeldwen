package jeldwen.beacon.message.model.message.request.impl.sensor;

import jeldwen.beacon.message.model.message.request.BaseRequestMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ForceSensorTriggerRequest extends BaseRequestMessage {

	/* Constants */
	public static final String NAME = "force-sensor-trigger";
	
	/* Constructor */
	public ForceSensorTriggerRequest() {
		setName(NAME);
	}
	
}