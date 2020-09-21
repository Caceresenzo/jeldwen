package jeldwen.backend.beacon.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.java_websocket.WebSocket;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jeldwen.backend.beacon.server.BeaconSocketServer;
import jeldwen.backend.beacon.service.IBeaconClientService;
import jeldwen.backend.beacon.service.IBeaconService;
import jeldwen.beacon.message.model.IBeaconMessage;
import jeldwen.beacon.message.model.config.BeaconConfig;
import jeldwen.beacon.message.model.request.impl.auth.AuthRequestMessage;
import jeldwen.beacon.message.model.request.impl.config.ConfigRequestMessage;
import jeldwen.beacon.message.model.response.impl.auth.AuthResponseMessage;
import jeldwen.beacon.message.model.response.impl.config.ConfigResponseMessage;
import jeldwen.beacon.message.service.IBeaconMessageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BeaconClientServiceImpl implements IBeaconClientService, DisposableBean {
	
	@Autowired
	private IBeaconMessageService beaconMessageService;
	
	@Autowired
	private IBeaconService beaconService;
	
	/* Variables */
	private final Map<WebSocket, String> socketToUniqueMap;
	private final Map<String, WebSocket> uniqueToSocketMap;
	private BeaconSocketServer server;
	
	/* Constructor */
	public BeaconClientServiceImpl() {
		this.socketToUniqueMap = new HashMap<>();
		this.uniqueToSocketMap = new HashMap<>();
	}
	
	@PostConstruct
	private void initialize() {
		server = new BeaconSocketServer(5600);
		
		server.start();
		server.setOnMessage(this::parseAndDispatchMessage);
		server.setOnClose(this::handleSocketDisconnect);
	}
	
	@Override
	public void destroy() throws Exception {
		server.stop();
	}
	
	@SneakyThrows
	private void parseAndDispatchMessage(WebSocket webSocket, String message) {
		IBeaconMessage beaconMessage = beaconMessageService.parse(message);
		
		if (beaconMessage instanceof AuthRequestMessage) {
			onBeaconRequestAuthentication(webSocket, (AuthRequestMessage) beaconMessage);
		} else if (beaconMessage instanceof ConfigRequestMessage) {
			onBeaconRequestConfig(webSocket, (ConfigRequestMessage) beaconMessage);
		}
	}
	
	private void handleSocketConnect(WebSocket webSocket, String unique) {
		socketToUniqueMap.put(webSocket, unique);
		uniqueToSocketMap.put(unique, webSocket);
		
		log.info("Socket identified as `{}` connected.", unique);
	}
	
	private void handleSocketDisconnect(WebSocket webSocket, String reason) {
		String unique = socketToUniqueMap.remove(webSocket);
		
		if (unique != null) {
			uniqueToSocketMap.remove(unique);
			
			log.info("Socket identified as `{}` disconnected. ({})", unique, reason);
		}
	}
	
	private boolean answer(WebSocket webSocket, IBeaconMessage message) {
		try {
			webSocket.send(beaconMessageService.stringify(message));
			
			return true;
		} catch (Exception exception) {
			throw new IllegalStateException("Cannot responde", exception);
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
				
				return answer(webSocket, new ConfigResponseMessage().setBeacon(beaconConfig));
			} catch (Exception exception) {
				log.error("Failed to anwser config with unique: " + unique, exception);
				
				return answer(webSocket, new ConfigResponseMessage().setReason(ConfigResponseMessage.Reason.SERVER_EXCEPTION));
			}
		}
		
		return answer(webSocket, new ConfigResponseMessage().setReason(ConfigResponseMessage.Reason.NOT_AUTHENTICATED));
	}
	
}