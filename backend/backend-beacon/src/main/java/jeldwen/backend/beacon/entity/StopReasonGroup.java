package jeldwen.backend.beacon.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jeldwen.backend.beacon.entity.interfaces.IStopReasonParent;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "stop_reason_groups")
@Data
@Accessors(chain = true)
public class StopReasonGroup implements IStopReasonParent {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private String name;
	
	@OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
	private Set<StopReason> children;
	
	@JsonIgnore
	public int getChildrenCount() {
		return children != null ? children.size() : null;
	}
	
	@Override
	public Set<StopReason> getStopReasonChildren() {
		return children;
	}
	
}