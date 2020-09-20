package jeldwen.beacon.message.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.greenrobot.eventbus.EventBus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jeldwen.beacon.message.model.IBeaconMessage;
import jeldwen.beacon.message.model.MessageType;
import jeldwen.beacon.message.model.request.BaseRequestMessage;
import jeldwen.beacon.message.model.request.impl.auth.AuthRequestMessage;
import jeldwen.beacon.message.model.response.BaseResponseMessage;
import jeldwen.beacon.message.model.response.impl.auth.AuthResponseMessage;
import jeldwen.beacon.message.service.IBeaconMessageService;

@Service
public class BeaconMessageServiceImpl implements IBeaconMessageService {
	
	/* Constants */
	public static final TypeReference<Map<String, Object>> TYPE_MAP_STRING_OBJECT = new TypeReference<Map<String, Object>>() {
	};
	
	/* Variables */
	private final ObjectMapper objectMapper;
	private final Map<String, Class<? extends IBeaconMessage>> requestClasses;
	private final Map<String, Class<? extends IBeaconMessage>> responseClasses;
	
	/* Constructor */
	public BeaconMessageServiceImpl() {
		this.objectMapper = new ObjectMapper();
		this.requestClasses = new HashMap<>();
		this.responseClasses = new HashMap<>();
	}
	
	@PostConstruct
	private void initialize() {
		requestClasses.put(AuthRequestMessage.NAME, AuthRequestMessage.class);
		responseClasses.put(AuthResponseMessage.NAME, AuthResponseMessage.class);
	}
	
	@Override
	public IBeaconMessage parse(String json) throws Exception {
		Map<String, Object> map = objectMapper.readValue(json, TYPE_MAP_STRING_OBJECT);
		
		String name = String.valueOf(map.get(JSON_KEY_NAME));
		MessageType type = MessageType.valueOf(String.valueOf(map.get(JSON_KEY_TYPE)));
		
		Map<String, Class<? extends IBeaconMessage>> classes;
		Class<? extends IBeaconMessage> defaultClass;
		
		if (MessageType.REQUEST.equals(type)) {
			classes = requestClasses;
			defaultClass = BaseRequestMessage.class;
		} else if (MessageType.RESPONSE.equals(type)) {
			classes = responseClasses;
			defaultClass = BaseResponseMessage.class;
		} else {
			throw new IllegalStateException("unexpected message type: " + type);
		}
		
		return objectMapper.convertValue(map, classes.getOrDefault(name, defaultClass));
	}
	
	@Override
	public String stringify(IBeaconMessage message) throws Exception {
		return objectMapper.writeValueAsString(message);
	}
	
	@Override
	public void parseAndDispatch(String json) throws Exception {
		dispatch(parse(json));
	}
	
	@Override
	public void dispatch(IBeaconMessage message) {
		EventBus.getDefault().post(message);
	}
	
	@Override
	public void register(Object object) {
		EventBus.getDefault().register(object);
	}
	
	@Override
	public void unregister(Object object) {
		EventBus.getDefault().unregister(object);
	}
	
}