package jeldwen.backend.beacon.dto;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BeaconUpdateBody {
	
	@Size(max = 255)
	private String name;
	
	@NotNull
	private List<Long> productFamilyIds;
	
	@NotNull
	private List<Long> stopReasonGroupIds;
	
	@NotNull
	private List<Long> stopReasonIds;
	
	@Min(0)
	@Max(1)
	private double syntheticPerformanceRateThreshold;
	
}