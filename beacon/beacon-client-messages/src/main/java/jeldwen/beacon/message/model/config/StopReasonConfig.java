package jeldwen.beacon.message.model.config;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StopReasonConfig {
	
	private long id;
	private String name;
	private long categoryId;
	
}