package jeldwen.backend.beacon.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StopReasonUpdateBody {
	
	@Size(max = 255)
	private String name;
	
	@Positive
	private long categoryId;
	
}