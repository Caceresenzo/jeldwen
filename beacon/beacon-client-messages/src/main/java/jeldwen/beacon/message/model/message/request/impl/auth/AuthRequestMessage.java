package jeldwen.beacon.message.model.message.request.impl.auth;

import jeldwen.beacon.message.model.message.request.BaseRequestMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AuthRequestMessage extends BaseRequestMessage {

	/* Constants */
	public static final String NAME = "auth";
	
	/* Variables */
	private String unique;
	
	/* Constructor */
	public AuthRequestMessage() {
		setName(NAME);
	}
	
}