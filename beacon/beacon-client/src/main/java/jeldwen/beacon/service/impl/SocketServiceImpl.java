package jeldwen.beacon.service.impl;

import java.net.URI;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jeldwen.beacon.client.BeaconSocketClient;
import jeldwen.beacon.message.model.IBeaconMessage;
import jeldwen.beacon.message.model.config.BeaconConfig;
import jeldwen.beacon.message.model.response.impl.auth.AuthResponseMessage;
import jeldwen.beacon.message.service.IBeaconMessageService;
import jeldwen.beacon.service.ISocketService;
import lombok.SneakyThrows;

@Service
public class SocketServiceImpl implements ISocketService, DisposableBean {
	
	@Autowired
	private IBeaconMessageService beaconMessageService;
	
	@Value("${server.uri}")
	private String serverUri;
	
	/* Variables */
	private BeaconSocketClient beaconSocketClient;
	private boolean finishing;
	
	@PostConstruct
	private void initialize() {
		finishing = false;
		
		beaconSocketClient = new BeaconSocketClient(URI.create(serverUri));
		beaconSocketClient.connect();
		
		beaconSocketClient.setOnMessage(this::handleMessage);
		beaconSocketClient.setOnOpen((handshake) -> authenticate());
	}
	
	@SneakyThrows
	public void authenticate() {
//		send(new AuthRequestMessage());
		send(new AuthResponseMessage().setConfig(new BeaconConfig()));
	}
	
	@SneakyThrows
	public void handleMessage(String message) {
		beaconMessageService.parseAndDispatch(message);
	}
	
	@SneakyThrows
	public void send(IBeaconMessage message) {
		beaconSocketClient.send(beaconMessageService.stringify(message));
	}
	
	@Override
	public void destroy() throws Exception {
		finishing = true;
		
		beaconSocketClient.closeBlocking();
	}
	
	@Scheduled(fixedRate = 10000)
	public void autoReconnect() throws InterruptedException {
		if (!finishing && beaconSocketClient != null && !beaconSocketClient.isOpen() && beaconSocketClient.isClosed()) {
			beaconSocketClient.reconnectBlocking();
		}
	}
	
}