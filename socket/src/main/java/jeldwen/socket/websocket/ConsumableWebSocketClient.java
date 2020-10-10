package jeldwen.socket.websocket;

import java.net.URI;
import java.util.function.Consumer;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Slf4j
public class ConsumableWebSocketClient extends WebSocketClient {
	
	/* Consumers */
	private Consumer<ServerHandshake> onOpen;
	private Consumer<String> onClose;
	private Consumer<String> onMessage;
	private Consumer<Exception> onError;
	
	/* Constructor */
	public ConsumableWebSocketClient(URI serverUri) {
		super(serverUri);
	}
	
	@Override
	public void onOpen(ServerHandshake handshake) {
		if (onOpen != null) {
			onOpen.accept(handshake);
		} else {
			log.info("onOpen: {}", handshake);
		}
	}
	
	@Override
	public void onMessage(String message) {
		if (onMessage != null) {
			onMessage.accept(message);
		} else {
			log.info("onMessage: {}", message);
		}
	}
	
	@Override
	public void onClose(int code, String reason, boolean remote) {
		if (onClose != null) {
			onClose.accept(reason);
		} else {
			log.info("onClose: {}, {}, {}", code, reason, remote);
		}
	}
	
	@Override
	public void onError(Exception exception) {
		if (onError != null) {
			onError.accept(exception);
		} else {
			log.info("onError", exception);
		}
	}
	
}