package jeldwen.backend.beacon.entity.interfaces;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jeldwen.backend.beacon.entity.StopReason;

public interface IStopReasonParent {
	
	long getId();
	
	String getName();
	
	@JsonIgnore
	Set<StopReason> getStopReasonChildren();
	
}