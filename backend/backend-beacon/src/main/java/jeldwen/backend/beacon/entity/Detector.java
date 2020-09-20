package jeldwen.backend.beacon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "detectors", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "unique" })
})
@Data
@Accessors(chain = true)
public class Detector {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private String unique;
	
	@Column
	private String name;
	
	@ManyToOne
	private Beacon beacon;

	@Column
	private long connectCount = -1;

	public Detector incrementConnectCount() {
		connectCount++;
		
		return this;
	}
	
}