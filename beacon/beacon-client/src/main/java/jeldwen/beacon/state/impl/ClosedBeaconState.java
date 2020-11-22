package jeldwen.beacon.state.impl;

import jeldwen.beacon.entity.HourPerHour;
import jeldwen.beacon.message.model.config.BeaconConfig;
import jeldwen.beacon.message.model.config.ProductFamilyConfig;
import jeldwen.beacon.message.model.config.StopReasonConfig;
import jeldwen.beacon.service.IBeaconService;
import jeldwen.beacon.state.IBeaconState;

public class ClosedBeaconState implements IBeaconState {
	
	@Override
	public void start(IBeaconService service, BeaconConfig beaconConfig, HourPerHour hourPerHour) {
		
	}
	
	@Override
	public void open(IBeaconService service) {
		service.setState(new WaitingProductFamilyBeaconState());
	}
	
	@Override
	public void close(IBeaconService service) {
		throw new IllegalStateException("already close");
	}
	
	@Override
	public boolean tick(IBeaconService service, HourPerHour hourPerHour) {
		return throwImpossible();
	}
	
	@Override
	public boolean changeProductFamily(IBeaconService service, ProductFamilyConfig family) {
		return throwImpossible();
	}
	
	@Override
	public boolean reportStopReason(IBeaconService service, StopReasonConfig stopReason) {
		return throwImpossible();
	}
	
	@Override
	public boolean isOpen(IBeaconService service) {
		return false;
	}
	
	@Override
	public boolean isStop(IBeaconService service) {
		return true;
	}
	
	@Override
	public boolean onNewConfig(IBeaconService service, BeaconConfig config) {
		return throwImpossible();
	}
	
	@Override
	public boolean onSensorTrigger(IBeaconService service, HourPerHour hourPerHour, boolean forced) {
		return throwImpossible();
	}
	
	@Override
	public Long getActiveProductFamily() {
		return null;
	}
	
	@Override
	public Long getSeconds() {
		return null;
	}
	
	@Override
	public String getName() {
		return "CLOSED";
	}
	
	private boolean throwImpossible() {
		throw new IllegalStateException("impossible while closed");
	}
	
}