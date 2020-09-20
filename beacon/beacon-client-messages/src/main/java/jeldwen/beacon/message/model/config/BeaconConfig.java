package jeldwen.beacon.message.model.config;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BeaconConfig {
	
	private List<StopReasonConfig> stopReasons;
	private List<StopReasonCategoryConfig> stopReasonCategories;
	
}