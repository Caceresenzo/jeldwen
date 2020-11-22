package jeldwen.backend.beacon.model.descriptor;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SimpleStopReasonCategoryDescriptor {
	
	private long id;
	private String name;
	private String color;
	
}