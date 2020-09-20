package jeldwen.beacon.message.model.config;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StopReasonCategoryConfig {
	
	private String key;
	private String name;
	private String color;
	
}