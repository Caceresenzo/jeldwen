package jeldwen.beacon.service;

import java.util.List;

import jeldwen.beacon.entity.HourPerHour;
import jeldwen.beacon.model.descriptor.SimpleHourPerHourDescriptor;

public interface IHourPerHourService {
	
	HourPerHour current();
	
	SimpleHourPerHourDescriptor currentDescriptor();

	List<SimpleHourPerHourDescriptor> history();
	
	void report();
	
}