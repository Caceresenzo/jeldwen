package jeldwen.beacon.message.model.message.response.impl.report;

import jeldwen.beacon.message.model.message.response.BaseResponseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ReportedStopReasonResponse extends BaseResponseMessage {
	
	/* Constants */
	public static final String NAME = "reported-stop-reason";
	
	/* Variables */
	private long id;
	
	/* Constructor */
	public ReportedStopReasonResponse() {
		setName(NAME);
	}
	
}