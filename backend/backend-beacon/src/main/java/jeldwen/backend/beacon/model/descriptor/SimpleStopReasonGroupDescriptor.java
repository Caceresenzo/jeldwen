package jeldwen.backend.beacon.model.descriptor;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SimpleStopReasonGroupDescriptor {
	
	private long id;
	private String name;
	
}