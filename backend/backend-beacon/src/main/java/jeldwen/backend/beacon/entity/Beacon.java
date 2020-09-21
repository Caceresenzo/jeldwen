package jeldwen.backend.beacon.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "beacons", uniqueConstraints = {
		@UniqueConstraint(columnNames = "unique")
})
@Data
@Accessors(chain = true)
public class Beacon {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String unique;
	
	@Column
	private String name;
	
	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name = "stop_reason_group_id"))
	private List<StopReasonGroup> stopReasonGroups;
	
	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name = "stop_reason_id"))
	private List<StopReason> stopReasons;
	
	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name = "product_family_id"))
	private List<ProductFamily> productFamilies;
	
	@Column
	private double syntheticPerformanceRateThreshold;
	
}