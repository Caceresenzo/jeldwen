package jeldwen.backend.beacon.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StopReasonGroupUpdateBody {
	
	@Size(max = 255)
	private String name;
	
	@NotNull
	private List<Long> childrenIds;
	
}