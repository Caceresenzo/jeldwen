package jeldwen.backend.beacon.model.descriptor;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SimpleBeaconSocketDescriptor {
	
	private String ip;
	private int port;
	private String unique;
	
}