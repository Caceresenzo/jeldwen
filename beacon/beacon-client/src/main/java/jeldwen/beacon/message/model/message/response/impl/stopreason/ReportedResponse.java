package jeldwen.beacon.message.model.message.response.impl.stopreason;

import jeldwen.beacon.message.model.message.response.BaseResponseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ReportedResponse extends BaseResponseMessage {
	
	/* Constants */
	public static final String NAME = "reported";
	
	/* Variables */
	private boolean success;
	
	/* Constructor */
	public ReportedResponse() {
		setName(NAME);
	}
	
}