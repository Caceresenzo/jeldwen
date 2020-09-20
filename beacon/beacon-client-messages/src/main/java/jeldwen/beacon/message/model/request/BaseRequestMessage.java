package jeldwen.beacon.message.model.request;

import jeldwen.beacon.message.model.BaseBeaconMessage;
import jeldwen.beacon.message.model.MessageType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BaseRequestMessage extends BaseBeaconMessage {
	
	@Override
	public MessageType getType() {
		return MessageType.REQUEST;
	}
	
}