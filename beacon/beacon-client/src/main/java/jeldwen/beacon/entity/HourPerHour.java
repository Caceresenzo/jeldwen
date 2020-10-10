package jeldwen.beacon.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "hour_per_hour")
@Data
@Accessors(chain = true)
public class HourPerHour {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private LocalDate date;
	
	@Column
	private long hour;
	
	@Column
	private long open;
	
	@Column
	private long stop;
	
	@Column
	private long produced;
	
	@Column
	private long objective;
	
	public HourPerHour incrementOpen() {
		open++;
		
		return this;
	}
	
	public HourPerHour incrementStop() {
		stop++;
		
		return this;
	}
	
	public HourPerHour incrementProduced() {
		produced++;
		
		return this;
	}
	
	public HourPerHour incrementObjective() {
		objective++;
		
		return this;
	}
	
}