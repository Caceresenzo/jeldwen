package jeldwen.backend.beacon.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import jeldwen.backend.beacon.entity.interfaces.IStopReasonParent;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "beacons", uniqueConstraints = {
		@UniqueConstraint(columnNames = "unique")
})
@Data
@Accessors(chain = true)
public class Beacon implements IStopReasonParent {
	
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
	
	@OneToMany(mappedBy = "beacon", fetch = FetchType.LAZY)
	private Set<StopReason> stopReasons;
	
	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name = "product_family_id"))
	private List<ProductFamily> productFamilies;
	
	@Column
	private double syntheticPerformanceRateThreshold;
	
	@Override
	public Set<StopReason> getStopReasonChildren() {
		return stopReasons;
	}
	
}