package jeldwen.beacon.message.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public abstract class BaseBeaconMessage implements IBeaconMessage {
	
	/* Variables */
	private String name;
	private @JsonIgnore Map<String, Object> properties;
	
	@JsonAnySetter
	public void anySetter(String propertyKey, Object value) {
		if (properties == null) {
			properties = new HashMap<>();
		}
		
		properties.put(propertyKey, value);
	}
	
	@JsonAnyGetter
	public Map<String, Object> anyGetter() {
		return properties == null ? Collections.emptyMap() : properties;
	}
	
}