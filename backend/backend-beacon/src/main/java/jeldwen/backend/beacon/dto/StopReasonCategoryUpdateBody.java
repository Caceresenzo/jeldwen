package jeldwen.backend.beacon.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StopReasonCategoryUpdateBody {
	
	@Size(max = 255)
	private String name;
	
	@NotNull
	private String color;
	
}