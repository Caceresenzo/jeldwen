package jeldwen.backend.beacon.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "stop_reasons")
@Embeddable
@Data
@Accessors(chain = true)
public class StopReason {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private String name;
	
	@ManyToOne
	private StopReasonCategory category;
	
	@Column
	private boolean attached;
	
	public StopReason attach() {
		return setAttached(true);
	}
	
}