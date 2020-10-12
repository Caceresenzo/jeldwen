package jeldwen.beacon.state.impl;

import java.time.Duration;
import java.time.LocalDateTime;

import jeldwen.beacon.entity.HourPerHour;
import jeldwen.beacon.message.model.config.BeaconConfig;
import jeldwen.beacon.message.model.config.ProductFamilyConfig;
import jeldwen.beacon.message.model.config.StopReasonConfig;
import jeldwen.beacon.service.IBeaconService;
import jeldwen.beacon.state.IBeaconState;
import jeldwen.beacon.util.Utility;

public class OpenedBeaconState implements IBeaconState {
	
	/* Variables */
	private ProductFamilyConfig activeProductFamily;
	private LocalDateTime objective, last;
	
	@Override
	public void start(IBeaconService service, BeaconConfig beaconConfig, HourPerHour hourPerHour) {
		resetBoth();
	}
	
	@Override
	public void open(IBeaconService service) {
		throw new IllegalStateException("already open");
	}
	
	@Override
	public void close(IBeaconService service) {
		service.setState(new ClosedBeaconState());
	}
	
	@Override
	public boolean tick(IBeaconService service, HourPerHour hourPerHour) {
		hourPerHour.incrementOpen();
		
		if (isStop()) {
			hourPerHour.incrementStop();
		}
		
		if (isObjectiveComplete()) {
			hourPerHour.incrementObjective();
			resetObjective();
		}
		
		return true;
	}
	
	@Override
	public boolean changeProductFamily(IBeaconService service, ProductFamilyConfig family) {
		if (family == null) {
			return false;
		}
		
		if (activeProductFamily == null || (activeProductFamily.getId() != family.getId())) {
			activeProductFamily = family;
			resetBoth();
			
			service.notifyState();
		}
		
		return true;
	}
	
	@Override
	public boolean reportStopReason(IBeaconService service, StopReasonConfig stopReason) {
		if (stopReason != null) {
			resetLast(); // TODO Implement buffering and flushing
			
			service.notifyState();
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isOpen(IBeaconService service) {
		return true;
	}
	
	@Override
	public boolean isStop(IBeaconService service) {
		return false;
	}
	
	@Override
	public boolean onNewConfig(IBeaconService service, BeaconConfig config) {
		if (activeProductFamily == null || !Utility.contains(config.getProductFamilies(), activeProductFamily.getId(), ProductFamilyConfig::getId)) {
			service.setState(new WaitingProductFamilyBeaconState());
		}
		
		return true;
	}
	
	@Override
	public boolean onSensorTrigger(IBeaconService service, HourPerHour hourPerHour, boolean forced) {
		resetLast();
		
		hourPerHour.incrementProduced();
		
		return true;
	}
	
	@Override
	public String getName() {
		return "OPENED";
	}
	
	@Override
	public Long getActiveProductFamily() {
		return activeProductFamily != null ? activeProductFamily.getId() : null;
	}
	
	@Override
	public Long getSeconds() {
		return Duration.between(last, LocalDateTime.now()).getSeconds();
	}
	
	public boolean isStop() {
		if (last != null && activeProductFamily != null) {
			return last.plusSeconds(activeProductFamily.getCycleTime()).isBefore(LocalDateTime.now());
		}
		
		return false;
	}
	
	public boolean isObjectiveComplete() {
		if (objective != null && activeProductFamily != null) {
			return objective.plusSeconds(activeProductFamily.getCycleTime()).isBefore(LocalDateTime.now());
		}
		
		return false;
	}
	
	private void resetBoth() {
		resetLast();
		resetObjective();
	}
	
	private void resetLast() {
		last = LocalDateTime.now();
	}
	
	private void resetObjective() {
		objective = LocalDateTime.now();
	}
	
}