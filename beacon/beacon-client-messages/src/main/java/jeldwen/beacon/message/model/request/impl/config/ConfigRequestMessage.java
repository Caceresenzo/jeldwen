package jeldwen.beacon.message.model.request.impl.config;

import jeldwen.beacon.message.model.request.BaseRequestMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ConfigRequestMessage extends BaseRequestMessage {

	/* Constants */
	public static final String NAME = "config";
	
	/* Constructor */
	public ConfigRequestMessage() {
		setName(NAME);
	}
	
}