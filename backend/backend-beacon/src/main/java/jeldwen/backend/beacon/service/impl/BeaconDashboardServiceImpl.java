package jeldwen.backend.beacon.service.impl;

import javax.annotation.PostConstruct;

import org.greenrobot.eventbus.Subscribe;
import org.java_websocket.WebSocket;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jeldwen.backend.beacon.server.ConsumableSocketServer;
import jeldwen.backend.beacon.service.IBeaconClientService;
import jeldwen.backend.beacon.service.IBeaconDashboardService;
import jeldwen.beacon.message.model.message.event.impl.connect.BeaconConnectedEventMessage;
import jeldwen.beacon.message.model.message.event.impl.connect.BeaconDisconnectedEventMessage;
import jeldwen.beacon.message.model.message.response.impl.list.ConnectedBeaconListResponseMessage;
import jeldwen.beacon.message.service.IBeaconMessageService;
import lombok.SneakyThrows;

@Service
public class BeaconDashboardServiceImpl implements IBeaconDashboardService, DisposableBean {
	
	@Autowired
	private IBeaconMessageService beaconMessageService;
	
	@Autowired
	private IBeaconClientService beaconClientService;
	
	/* Variables */
	private ConsumableSocketServer server;
	
	@PostConstruct
	private void initialize() {
		server = new ConsumableSocketServer(5700);
		
		server.start();
		server.setOnOpen((webSocket, handshake) -> handleSocketOpen(webSocket));
		
		beaconMessageService.register(this);
	}
	
	@Override
	public void destroy() throws Exception {
		beaconMessageService.unregister(this);
		
		if (server != null) {
			server.stop();
		}
	}
	
	@SneakyThrows
	private void handleSocketOpen(WebSocket webSocket) {
		((BeaconClientServiceImpl) beaconClientService).answer(webSocket, new ConnectedBeaconListResponseMessage().setUniques(beaconClientService.listConnectedUniques()));
	}
	
	@Subscribe
	@SneakyThrows
	public void onBeaconConnected(BeaconConnectedEventMessage message) {
		server.broadcast(beaconMessageService.stringify(message));
	}
	
	@Subscribe
	@SneakyThrows
	public void onBeaconDisconnected(BeaconDisconnectedEventMessage message) {
		server.broadcast(beaconMessageService.stringify(message));
	}
	
}