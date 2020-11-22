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
	
	public StopReasonConfig findStopReason(long id) {
		return findById(stopReasons, id);
	}
	
	public StopReasonCategoryConfig findStopReasonCategory(long id) {
		return findById(stopReasonCategories, id);
	}
	
	public ProductFamilyConfig findProductFamily(long id) {
		return findById(productFamilies, id);
	}
	
	private <T extends ConfigObject> T findById(List<T> list, long id) {
		return list.stream()
				.filter((object) -> object.getId() == id)
				.findFirst()
				.orElse(null);
	}
	
}