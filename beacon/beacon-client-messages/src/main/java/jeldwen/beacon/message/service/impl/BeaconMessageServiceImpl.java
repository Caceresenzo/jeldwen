package jeldwen.beacon.message.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.greenrobot.eventbus.EventBus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jeldwen.beacon.message.model.IBeaconMessage;
import jeldwen.beacon.message.model.MessageType;
import jeldwen.beacon.message.model.message.request.BaseRequestMessage;
import jeldwen.beacon.message.model.message.request.impl.auth.AuthRequestMessage;
import jeldwen.beacon.message.model.message.request.impl.config.ConfigRequestMessage;
import jeldwen.beacon.message.model.message.request.impl.report.StopReasonReportRequest;
import jeldwen.beacon.message.model.message.request.impl.sensor.ForceSensorTriggerRequest;
import jeldwen.beacon.message.model.message.request.impl.workstation.WorkstationCloseRequest;
import jeldwen.beacon.message.model.message.request.impl.workstation.WorkstationOpenRequest;
import jeldwen.beacon.message.model.message.response.BaseResponseMessage;
import jeldwen.beacon.message.model.message.response.impl.auth.AuthResponseMessage;
import jeldwen.beacon.message.model.message.response.impl.config.ConfigResponseMessage;
import jeldwen.beacon.message.model.message.response.impl.list.ConnectedBeaconListResponseMessage;
import jeldwen.beacon.message.model.message.response.impl.report.ReportedStopReasonResponse;
import jeldwen.beacon.message.service.IBeaconMessageService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		registerRequestClass(AuthRequestMessage.NAME, AuthRequestMessage.class);
		registerRequestClass(ConfigRequestMessage.NAME, ConfigRequestMessage.class);
		registerRequestClass(WorkstationOpenRequest.NAME, WorkstationOpenRequest.class);
		registerRequestClass(WorkstationCloseRequest.NAME, WorkstationCloseRequest.class);
		registerRequestClass(ForceSensorTriggerRequest.NAME, ForceSensorTriggerRequest.class);
		registerRequestClass(StopReasonReportRequest.NAME, StopReasonReportRequest.class);
		
		registerResponseClass(AuthResponseMessage.NAME, AuthResponseMessage.class);
		registerResponseClass(ConfigResponseMessage.NAME, ConfigResponseMessage.class);
		registerResponseClass(ConnectedBeaconListResponseMessage.NAME, ConnectedBeaconListResponseMessage.class);
		registerResponseClass(ReportedStopReasonResponse.NAME, ReportedStopReasonResponse.class);
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
		
		Class<? extends IBeaconMessage> messageClass = classes.getOrDefault(name, defaultClass);
		if (defaultClass.equals(messageClass)) {
			log.warn("No message class has been found for `{}` ({}). (has it been registered?)", name, type);
		}
		
		return objectMapper.convertValue(map, messageClass);
	}
	
	@Override
	public String stringify(IBeaconMessage message) throws Exception {
		return objectMapper.writeValueAsString(message);
	}
	
	@Override
	public IBeaconMessage parseAndDispatch(String json) throws Exception {
		return dispatch(parse(json));
	}
	
	@Override
	public IBeaconMessage dispatch(IBeaconMessage message) {
		EventBus.getDefault().post(message);
		
		return message;
	}
	
	@Override
	public void register(Object object) {
		EventBus.getDefault().register(object);
	}
	
	@Override
	public void unregister(Object object) {
		EventBus.getDefault().unregister(object);
	}
	
	@Override
	public void registerRequestClass(String name, Class<? extends BaseRequestMessage> clazz) {
		requestClasses.put(name, clazz);
	}
	
	@Override
	public void registerResponseClass(String name, Class<? extends BaseResponseMessage> clazz) {
		responseClasses.put(name, clazz);
	}
	
}