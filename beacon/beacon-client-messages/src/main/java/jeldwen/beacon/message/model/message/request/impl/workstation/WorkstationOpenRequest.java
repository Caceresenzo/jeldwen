package jeldwen.beacon.message.model.message.request.impl.workstation;

import jeldwen.beacon.message.model.message.request.BaseRequestMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WorkstationOpenRequest extends BaseRequestMessage {

	/* Constants */
	public static final String NAME = "workstation-open";
	
	/* Constructor */
	public WorkstationOpenRequest() {
		setName(NAME);
	}
	
}