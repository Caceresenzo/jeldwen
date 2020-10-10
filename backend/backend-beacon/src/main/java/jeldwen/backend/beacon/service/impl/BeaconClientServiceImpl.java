package jeldwen.backend.beacon.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.java_websocket.WebSocket;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jeldwen.backend.beacon.service.IBeaconClientService;
import jeldwen.backend.beacon.service.IBeaconService;
import jeldwen.beacon.message.model.IBeaconMessage;
import jeldwen.beacon.message.model.config.BeaconConfig;
import jeldwen.beacon.message.model.message.event.impl.connect.BeaconConnectedEventMessage;
import jeldwen.beacon.message.model.message.event.impl.connect.BeaconDisconnectedEventMessage;
import jeldwen.beacon.message.model.message.request.impl.auth.AuthRequestMessage;
import jeldwen.beacon.message.model.message.request.impl.config.ConfigRequestMessage;
import jeldwen.beacon.message.model.message.request.impl.sensor.ForceSensorTriggerRequest;
import jeldwen.beacon.message.model.message.response.impl.auth.AuthResponseMessage;
import jeldwen.beacon.message.model.message.response.impl.config.ConfigResponseMessage;
import jeldwen.beacon.message.service.IBeaconMessageService;
import jeldwen.socket.websocket.ConsumableWebSocketServer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BeaconClientServiceImpl implements IBeaconClientService, DisposableBean {
	
	/* Constants */
	private static final Object LOCK = new Object();
	
	@Autowired
	private IBeaconMessageService beaconMessageService;
	
	@Autowired
	private IBeaconService beaconService;
	
	/* Variables */
	private final Map<WebSocket, String> socketToUniqueMap;
	private final Map<String, WebSocket> uniqueToSocketMap;
	private ConsumableWebSocketServer server;
	
	/* Constructor */
	public BeaconClientServiceImpl() {
		this.socketToUniqueMap = new HashMap<>();
		this.uniqueToSocketMap = new HashMap<>();
	}
	
	@PostConstruct
	private void initialize() {
		server = new ConsumableWebSocketServer(5600);
		
		server.start();
		server.setOnMessage(this::parseAndDispatchMessage);
		server.setOnClose(this::handleSocketDisconnect);
	}
	
	@Override
	public void destroy() throws Exception {
		server.stop();
	}
	
	@Override
	public List<String> listConnectedUniques() {
		synchronized (LOCK) {
			return new ArrayList<>(socketToUniqueMap.values());
		}
	}
	
	@Override
	public boolean reconfigure(String unique) {
		return answer(unique, new ConfigResponseMessage()
				.setForced(true)
				.setBeaconConfig(beaconService.getConfig(unique)));
	}
	
	@Override
	public boolean forceTrigger(String unique) {
		return answer(unique, new ForceSensorTriggerRequest());
	}
	
	@SneakyThrows
	private void parseAndDispatchMessage(WebSocket webSocket, String message) {
		IBeaconMessage beaconMessage = beaconMessageService.parseAndDispatch(message);
		
		if (beaconMessage instanceof AuthRequestMessage) {
			onBeaconRequestAuthentication(webSocket, (AuthRequestMessage) beaconMessage);
		} else if (beaconMessage instanceof ConfigRequestMessage) {
			onBeaconRequestConfig(webSocket, (ConfigRequestMessage) beaconMessage);
		}
	}
	
	private void handleSocketConnect(WebSocket webSocket, String unique) {
		synchronized (LOCK) {
			socketToUniqueMap.put(webSocket, unique);
			uniqueToSocketMap.put(unique, webSocket);
		}
		
		log.info("Socket identified as `{}` connected.", unique);
		
		beaconMessageService.dispatch(new BeaconConnectedEventMessage().setUnique(unique));
	}
	
	private void handleSocketDisconnect(WebSocket webSocket, String reason) {
		String unique;
		
		synchronized (LOCK) {
			unique = socketToUniqueMap.remove(webSocket);
		}
		
		if (unique != null) {
			synchronized (LOCK) {
				uniqueToSocketMap.remove(unique);
			}
			
			log.info("Socket identified as `{}` disconnected. ({})", unique, reason);
			
			beaconMessageService.dispatch(new BeaconDisconnectedEventMessage().setUnique(unique));
		}
	}
	
	public boolean answer(String unique, IBeaconMessage message) {
		return answer(uniqueToSocketMap.get(unique), message);
	}
	
	public boolean answer(WebSocket webSocket, IBeaconMessage message) {
		if (webSocket == null) {
			return false;
		}
		
		try {
			webSocket.send(beaconMessageService.stringify(message));
			
			return true;
		} catch (Exception exception) {
			throw new IllegalStateException("Cannot answer", exception);
		}
	}
	
	private boolean onBeaconRequestAuthentication(WebSocket webSocket, AuthRequestMessage message) {
		String unique = message.getUnique();
		
		if (uniqueToSocketMap.containsKey(unique)) {
			return answer(webSocket, new AuthResponseMessage().setReason(AuthResponseMessage.Reason.ALREADY_CONNECTED));
		}
		
		try {
			if (beaconService.create(unique)) {
				log.info("Beacon identified as `{}` has been created.", unique);
			}
			
			handleSocketConnect(webSocket, unique);
			
			return answer(webSocket, new AuthResponseMessage());
		} catch (Exception exception) {
			log.error("Failed to authenticate with unique: " + unique, exception);
			
			return answer(webSocket, new AuthResponseMessage().setReason(AuthResponseMessage.Reason.SERVER_EXCEPTION));
		}
	}
	
	private boolean onBeaconRequestConfig(WebSocket webSocket, ConfigRequestMessage message) {
		String unique = socketToUniqueMap.get(webSocket);
		
		if (unique != null) {
			try {
				BeaconConfig beaconConfig = beaconService.getConfig(unique);
				
				if (beaconConfig == null) {
					return answer(webSocket, new ConfigResponseMessage().setReason(ConfigResponseMessage.Reason.NOT_CONFIGURED));
				}
				
				return answer(webSocket, new ConfigResponseMessage().setBeaconConfig(beaconConfig));
			} catch (Exception exception) {
				log.error("Failed to anwser config with unique: " + unique, exception);
				
				return answer(webSocket, new ConfigResponseMessage().setReason(ConfigResponseMessage.Reason.SERVER_EXCEPTION));
			}
		}
		
		return answer(webSocket, new ConfigResponseMessage().setReason(ConfigResponseMessage.Reason.NOT_AUTHENTICATED));
	}
	
}