package jeldwen.beacon.message.service;

import jeldwen.beacon.message.model.IBeaconMessage;
import jeldwen.beacon.message.model.message.request.BaseRequestMessage;
import jeldwen.beacon.message.model.message.response.BaseResponseMessage;

public interface IBeaconMessageService {
	
	/* Constants */
	public static final String JSON_KEY_NAME = "name";
	public static final String JSON_KEY_TYPE = "type";
	
	IBeaconMessage parse(String json) throws Exception;
	
	String stringify(IBeaconMessage message) throws Exception;
	
	IBeaconMessage parseAndDispatch(String json) throws Exception;
	
	IBeaconMessage dispatch(IBeaconMessage message);
	
	void register(Object object);
	
	void unregister(Object object);

	void registerRequestClass(String name, Class<? extends BaseRequestMessage> clazz);

	void registerResponseClass(String name, Class<? extends BaseResponseMessage> clazz);
	
}