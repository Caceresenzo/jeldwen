package jeldwen.beacon.message.model.message.request.impl.stopreason;

import jeldwen.beacon.message.model.message.request.BaseRequestMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ReportRequest extends BaseRequestMessage {

	/* Constants */
	public static final String NAME = "report";
	
	/* Variables */
	private long stopReasonId;
	
	/* Constructor */
	public ReportRequest() {
		setName(NAME);
	}
	
}