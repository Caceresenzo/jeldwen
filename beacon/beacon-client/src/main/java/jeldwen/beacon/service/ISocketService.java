package jeldwen.beacon.service;

import jeldwen.beacon.message.model.IBeaconMessage;

public interface ISocketService {
	
	public void send(IBeaconMessage message);
	
}