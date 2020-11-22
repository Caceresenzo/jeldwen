package jeldwen.beacon.service;

import jeldwen.beacon.message.model.IBeaconMessage;
import jeldwen.beacon.state.IBeaconState;

public interface IBeaconService {
	
	IBeaconState setState(IBeaconState state);
	
	IBeaconState getState();

	void open();
	
	void close();
	
	void tick();

	void notifyState();

	void notifyState(boolean includeHistory);

	void notify(IBeaconMessage message);

	void signal(boolean forced);
	
}