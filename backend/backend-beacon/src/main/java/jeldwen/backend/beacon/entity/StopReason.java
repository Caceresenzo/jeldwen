package jeldwen.backend.beacon.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jeldwen.backend.beacon.entity.interfaces.IStopReasonParent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Table(name = "stop_reasons")
@Embeddable
@Data
@EqualsAndHashCode(exclude = { "group", "beacon" })
@Accessors(chain = true)
public class StopReason {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private String name;
	
	@ManyToOne(optional = false)
	private StopReasonCategory category;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable = true)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private StopReasonGroup group;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "beacon_id", nullable = true)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private Beacon beacon;
	
	@JsonIgnore
	public Long getCategoryId() {
		return category != null ? category.getId() : null;
	}
	
	@JsonIgnore
	public IStopReasonParent getParent() {
		return group != null ? group : beacon;
	}
	
	@JsonIgnore
	public Long getParentId() {
		IStopReasonParent parent = getParent();
		
		return parent != null ? parent.getId() : null;
	}
	
	@JsonIgnore
	public boolean hasParent() {
		return getParent() != null;
	}
	
	@JsonIgnore
	public boolean isFree() {
		return getParent() == null;
	}
	
	@JsonIgnore
	public StopReason setParent(IStopReasonParent parent) {
		if (parent != null) {
			if (parent instanceof StopReasonGroup) {
				return setGroup((StopReasonGroup) parent);
			} else if (parent instanceof Beacon) {
				return setBeacon((Beacon) parent);
			}
			
			throw new IllegalStateException("unsupported parent: " + parent);
		}
		
		setGroup(null);
		setBeacon(null);
		
		return this;
	}
	
}