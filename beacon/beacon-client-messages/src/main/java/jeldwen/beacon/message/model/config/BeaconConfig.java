package jeldwen.beacon.message.model.config;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BeaconConfig {

	private String name;
	private List<StopReasonConfig> stopReasons;
	private List<StopReasonCategoryConfig> stopReasonCategories;
	private List<ProductFamilyConfig> productFamilies;
	private double syntheticPerformanceRateThreshold;
	
}