package jeldwen.beacon.message.model.message.event;

import jeldwen.beacon.message.model.BaseBeaconMessage;
import jeldwen.beacon.message.model.MessageType;

public class BaseEventMessage extends BaseBeaconMessage {
	
	@Override
	public MessageType getType() {
		return MessageType.EVENT;
	}
	
}