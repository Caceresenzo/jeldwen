package jeldwen.backend.beacon.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "reported_stop_reason", indexes = {
		@Index(columnList = "beacon_id"),
		@Index(columnList = "stop_reason_id"),
		@Index(columnList = "product_family_id"),
		@Index(columnList = "duration"),
		@Index(columnList = "at"),
})
@Data
@Accessors(chain = true)
public class ReportedStopReason {
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne(optional = false)
	private Beacon beacon;
	
	@ManyToOne
	private StopReason stopReason;
	
	@Column
	private String message;
	
	@ManyToOne
	private ProductFamily productFamily;
	
	@Column
	private long duration;
	
	@Column
	private LocalDateTime at;
	
}