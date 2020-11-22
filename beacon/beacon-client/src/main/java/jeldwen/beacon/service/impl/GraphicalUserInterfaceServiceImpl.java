package jeldwen.beacon.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jeldwen.beacon.message.model.IBeaconMessage;
import jeldwen.beacon.message.service.IBeaconMessageService;
import jeldwen.beacon.model.event.SocketConnectedEvent;
import jeldwen.beacon.service.IGraphicalUserInterfaceService;
import jeldwen.socket.websocket.ConsumableWebSocketServer;
import lombok.SneakyThrows;

@Service
public class GraphicalUserInterfaceServiceImpl implements IGraphicalUserInterfaceService, DisposableBean {
	
	@Autowired
	private IBeaconMessageService beaconMessageService;
	
	/* Variables */
	private ConsumableWebSocketServer server;
	
	@PostConstruct
	private void initialize() {
		server = new ConsumableWebSocketServer(5800);
		
		server.start();
		server.setOnOpen((webSocket, handshake) -> beaconMessageService.dispatch(new SocketConnectedEvent(webSocket)));
		server.setOnMessage((webSocket, message) -> handleSocketMessage(message));
	}
	
	@Override
	public void destroy() throws Exception {
		if (server != null) {
			server.stop();
		}
	}
	
	@SneakyThrows
	private void handleSocketMessage(String message) {
		beaconMessageService.parseAndDispatch(message);
	}
	
	@Override
	@SneakyThrows
	public void notify(IBeaconMessage message) {
		server.broadcast(beaconMessageService.stringify(message));
	}
	
}