package jeldwen.beacon.message.model.config;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StopReasonConfig {
	
	private String key;
	private String name;
	private String categoryKey;
	
}