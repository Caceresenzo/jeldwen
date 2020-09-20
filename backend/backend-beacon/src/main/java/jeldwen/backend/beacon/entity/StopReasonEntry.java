package jeldwen.backend.beacon.entity;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "stop_reason_entries")
@Data
@Accessors(chain = true)
public class StopReasonEntry {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private String key;

	@ManyToOne
	@JsonIgnore
	private Beacon beacon;
	
	@Column
	private LocalTime cumulated = LocalTime.MIN;
	
	@Column
	private long used;
	
	public StopReasonEntry incrementUsed() {
		used++;
		
		return this;
	}
	
	public StopReasonEntry addSecondsToCumulated(long seconds) {
		return setCumulated(cumulated.plus(seconds, ChronoUnit.SECONDS));
	}
	
}