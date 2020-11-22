package jeldwen.backend.beacon.model.descriptor;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SimpleBeaconDescriptor {
	
	private long id;
	private String unique;
	private String name;
	
}