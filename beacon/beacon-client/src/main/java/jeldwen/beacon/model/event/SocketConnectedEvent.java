package jeldwen.beacon.model.event;

import org.java_websocket.WebSocket;

import jeldwen.beacon.message.model.IBeaconMessage;
import jeldwen.beacon.message.model.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocketConnectedEvent implements IBeaconMessage {
	
	/* Variables */
	private WebSocket client;

	@Override
	public String getName() {
		throw new IllegalStateException();
	}

	@Override
	public MessageType getType() {
		throw new IllegalStateException();
	}
	
}