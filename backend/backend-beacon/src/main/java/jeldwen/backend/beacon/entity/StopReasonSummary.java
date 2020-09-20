package jeldwen.backend.beacon.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonRawValue;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "stop_reason_summaries")
@Data
@Accessors(chain = true)
public class StopReasonSummary {

	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private String name;
	
	@Column
	@Lob
	@JsonRawValue
	private String content;
	
	@Column
	private LocalDateTime at = LocalDateTime.now();
	
}