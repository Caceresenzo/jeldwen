package jeldwen.beacon.state.impl;

import jeldwen.beacon.entity.HourPerHour;
import jeldwen.beacon.message.model.config.BeaconConfig;
import jeldwen.beacon.message.model.config.ProductFamilyConfig;
import jeldwen.beacon.message.model.config.StopReasonConfig;
import jeldwen.beacon.service.IBeaconService;
import jeldwen.beacon.state.IBeaconState;

public class WaitingProductFamilyBeaconState implements IBeaconState {
	
	@Override
	public void start(IBeaconService service, BeaconConfig beaconConfig, HourPerHour hourPerHour) {
		
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
		return false;
	}
	
	@Override
	public boolean changeProductFamily(IBeaconService service, ProductFamilyConfig family) {
		return service.setState(new OpenedBeaconState()).changeProductFamily(service, family);
	}
	
	@Override
	public boolean reportStopReason(IBeaconService service, StopReasonConfig stopReason) {
		return false;
	}
	
	@Override
	public boolean isOpen(IBeaconService service) {
		return true;
	}
	
	@Override
	public boolean isStop(IBeaconService service) {
		return true;
	}
	
	@Override
	public boolean onNewConfig(IBeaconService service, BeaconConfig config) {
		return false;
	}
	
	@Override
	public boolean onSensorTrigger(IBeaconService service, HourPerHour hourPerHour, boolean forced) {
		return false;
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
		return "WAITING PRODUCT FAMILY";
	}
	
}