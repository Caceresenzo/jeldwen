package jeldwen.beacon.message.model.message.response;

import jeldwen.beacon.message.model.BaseBeaconMessage;
import jeldwen.beacon.message.model.MessageType;

public class BaseResponseMessage extends BaseBeaconMessage {
	
	@Override
	public MessageType getType() {
		return MessageType.RESPONSE;
	}
	
}