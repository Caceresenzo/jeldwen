package jeldwen.beacon.message.model.message.event;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jeldwen.beacon.message.model.BaseBeaconMessage;
import jeldwen.beacon.message.model.MessageType;

public class BaseEventMessage extends BaseBeaconMessage {
	
	@Override
	@JsonIgnore
	public MessageType getType() {
		return MessageType.EVENT;
	}
	
}