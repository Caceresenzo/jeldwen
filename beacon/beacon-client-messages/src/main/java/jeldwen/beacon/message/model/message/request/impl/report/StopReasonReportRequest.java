package jeldwen.beacon.message.model.message.request.impl.report;

import java.time.LocalDateTime;

import jeldwen.beacon.message.model.message.request.BaseRequestMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class StopReasonReportRequest extends BaseRequestMessage {
	
	/* Constants */
	public static final String NAME = "stop-reason-report";
	
	/* Variables */
	private long id;
	private Long stopReasonId;
	private String message;
	private long productFamilyId;
	private long duration;
	private LocalDateTime at;
	private boolean sent;
	
	/* Constructor */
	public StopReasonReportRequest() {
		setName(NAME);
	}
	
}