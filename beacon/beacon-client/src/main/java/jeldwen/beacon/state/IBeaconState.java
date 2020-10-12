package jeldwen.beacon.state;

import jeldwen.beacon.entity.HourPerHour;
import jeldwen.beacon.message.model.config.BeaconConfig;
import jeldwen.beacon.message.model.config.ProductFamilyConfig;
import jeldwen.beacon.message.model.config.StopReasonConfig;
import jeldwen.beacon.service.IBeaconService;

public interface IBeaconState {
	
	void start(IBeaconService service, BeaconConfig beaconConfig, HourPerHour hourPerHour);
	
	void open(IBeaconService service);
	
	void close(IBeaconService service);
	
	boolean tick(IBeaconService service, HourPerHour hourPerHour);
	
	boolean changeProductFamily(IBeaconService service, ProductFamilyConfig family);
	
	boolean reportStopReason(IBeaconService service, StopReasonConfig stopReason);
	
	boolean isOpen(IBeaconService service);
	
	boolean isStop(IBeaconService service);
	
	boolean onNewConfig(IBeaconService service, BeaconConfig config);
	
	boolean onSensorTrigger(IBeaconService service, HourPerHour hourPerHour, boolean forced);
	
	default void beforeStateChange(IBeaconService service, IBeaconState newState) {
	}

	Long getActiveProductFamily();

	Long getSeconds();
	
	String getName();
	
}