package jeldwen.backend.beacon.dto;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DetectorUpdateBody {
	
	@Column
	@Size(max = 255)
	private String name;
	
	@Column
	private long beacon;
	
}