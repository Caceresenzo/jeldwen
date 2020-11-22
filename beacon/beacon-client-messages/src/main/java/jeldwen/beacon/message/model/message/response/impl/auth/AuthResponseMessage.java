package jeldwen.beacon.message.model.message.response.impl.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jeldwen.beacon.message.model.message.response.BaseResponseMessage;
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
	private Reason reason;
	
	/* Constructor */
	public AuthResponseMessage() {
		setName(NAME);
	}
	
	@JsonIgnore
	public boolean isSuccess() {
		return reason == null;
	}
	
	public enum Reason {
		
		ALREADY_CONNECTED, SERVER_EXCEPTION;
	
	}
	
}