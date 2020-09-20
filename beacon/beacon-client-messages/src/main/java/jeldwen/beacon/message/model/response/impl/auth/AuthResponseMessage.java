package jeldwen.beacon.message.model.response.impl.auth;

import jeldwen.beacon.message.model.config.BeaconConfig;
import jeldwen.beacon.message.model.response.BaseResponseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AuthResponseMessage extends BaseResponseMessage {
	
	/* Constants */
	public static final String NAME = "auth";
	
	/* Variables */
	private String version;
	private BeaconConfig config;
	
	/* Constructor */
	public AuthResponseMessage() {
		setName(NAME);
	}
	
}