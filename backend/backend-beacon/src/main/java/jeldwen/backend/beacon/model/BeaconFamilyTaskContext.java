package jeldwen.backend.beacon.model;

import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.backend.beacon.entity.ProductFamily;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class BeaconFamilyTaskContext {
	
	private final Beacon beacon;
	private final ProductFamily productFamily;
	
	private final long startedAt = System.currentTimeMillis();
	
	public long getDelta() {
		return startedAt + (productFamily.getCycleTime() * 1000) - System.currentTimeMillis();
	}
	
	public long getDuration() {
		return (System.currentTimeMillis() - startedAt) / 1000;
	}
	
}