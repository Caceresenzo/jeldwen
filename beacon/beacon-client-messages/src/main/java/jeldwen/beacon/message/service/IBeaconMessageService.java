package jeldwen.beacon.message.service;

import jeldwen.beacon.message.model.IBeaconMessage;

public interface IBeaconMessageService {
	
	/* Constants */
	public static final String JSON_KEY_NAME = "name";
	public static final String JSON_KEY_TYPE = "type";
	
	IBeaconMessage parse(String json) throws Exception;
	
	String stringify(IBeaconMessage message) throws Exception;
	
	void parseAndDispatch(String json) throws Exception;
	
	void dispatch(IBeaconMessage message);
	
	void register(Object object);
	
	void unregister(Object object);
	
}