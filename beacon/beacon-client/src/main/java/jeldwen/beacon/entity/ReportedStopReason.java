package jeldwen.beacon.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "buffered_reported_stop_reasons")
@Data
@Accessors(chain = true)
public class ReportedStopReason {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private Long stopReasonId;
	
	@Column
	private String message;
	
	@Column
	private long productFamilyId;
	
	@Column
	private long duration;
	
	@Column
	private LocalDateTime at;
	
	@Column
	private boolean sent;
	
	public ReportedStopReason setAsNotSent() {
		return setSent(false);
	}
	
}