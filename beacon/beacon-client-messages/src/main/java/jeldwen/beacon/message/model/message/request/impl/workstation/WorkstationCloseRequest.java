package jeldwen.beacon.message.model.message.request.impl.workstation;

import jeldwen.beacon.message.model.message.request.BaseRequestMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WorkstationCloseRequest extends BaseRequestMessage {

	/* Constants */
	public static final String NAME = "workstation-close";
	
	/* Constructor */
	public WorkstationCloseRequest() {
		setName(NAME);
	}
	
}