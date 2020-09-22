package jeldwen.beacon.message.model.message.response.impl.config;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jeldwen.beacon.message.model.config.BeaconConfig;
import jeldwen.beacon.message.model.message.response.BaseResponseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ConfigResponseMessage extends BaseResponseMessage {
	
	/* Constants */
	public static final String NAME = "config";
	
	/* Variables */
	private BeaconConfig beaconConfig;
	private boolean forced;
	private Reason reason;
	
	/* Constructor */
	public ConfigResponseMessage() {
		setName(NAME);
	}
	
	@JsonIgnore
	public boolean isSuccess() {
		return reason == null;
	}
	
	public enum Reason {
		
		NOT_AUTHENTICATED, NOT_CONFIGURED, SERVER_EXCEPTION;
		
	}
	
}