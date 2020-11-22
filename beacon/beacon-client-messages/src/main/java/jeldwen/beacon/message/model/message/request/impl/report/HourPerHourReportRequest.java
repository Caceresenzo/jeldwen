package jeldwen.beacon.message.model.message.request.impl.report;

import jeldwen.beacon.message.model.message.request.BaseRequestMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class HourPerHourReportRequest extends BaseRequestMessage {
	
	/* Constants */
	public static final String NAME = "hour-per-hour-report";
	
	/* Variables */
	private long id;
	
	/* Constructor */
	public HourPerHourReportRequest() {
		setName(NAME);
	}
	
}