package jeldwen.backend.beacon.server;

import java.net.InetSocketAddress;
import java.util.function.BiConsumer;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Setter
@Accessors(chain = true)
@Slf4j
public class BeaconSocketServer extends WebSocketServer {
	
	/* Consumers */
	private BiConsumer<WebSocket, ClientHandshake> onOpen;
	private BiConsumer<WebSocket, String> onClose;
	private BiConsumer<WebSocket, String> onMessage;
	private BiConsumer<WebSocket, Exception> onError;
	
	/* Constructor */
	public BeaconSocketServer(int port) {
		super(new InetSocketAddress(port));
	}
	
	/* Constructor */
	public BeaconSocketServer(InetSocketAddress address) {
		super(address);
	}
	
	@Override
	public void onStart() {
		log.info("Beacon WebServer started on port: {}", getPort());
		
		setConnectionLostTimeout(0);
		setConnectionLostTimeout(100);
	}
	
	@Override
	public void onOpen(WebSocket webSocket, ClientHandshake handshake) {
		if (onOpen != null) {
			onOpen.accept(webSocket, handshake);
		} else {
			log.info("onOpen: {}", webSocket);
		}
	}
	
	@Override
	public void onClose(WebSocket webSocket, int code, String reason, boolean remote) {
		if (onClose != null) {
			onClose.accept(webSocket, reason);
		} else {
			log.info("onClose: {}, {}, {}, {}", webSocket, code, reason, remote);
		}
	}
	
	@Override
	public void onMessage(WebSocket webSocket, String message) {
		if (onMessage != null) {
			onMessage.accept(webSocket, message);
		} else {
			log.info("onMessage: {}: {}", webSocket, message);
		}
	}
	
	@Override
	public void onError(WebSocket webSocket, Exception exception) {
		if (onError != null) {
			onError.accept(webSocket, exception);
		} else {
			log.info("onError: {} ({})", webSocket, exception);
		}
	}
	
}