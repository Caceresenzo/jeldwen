package jeldwen.backend.beacon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "stop_reason_categories")
@Data
@Accessors(chain = true)
public class StopReasonCategory {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private String name;
	
	@Column
	private String color;
	
}