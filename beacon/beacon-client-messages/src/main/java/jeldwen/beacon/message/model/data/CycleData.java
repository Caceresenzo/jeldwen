package jeldwen.beacon.message.model.data;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CycleData {
	
	/* Variables */
	private int hour;
	private Map<Long, Map<Long, Long>> stopCountByIdByTimeMap = new HashMap<>();
	
	public long computeTotal() {
		long total = 0;
		
		if (stopCountByIdByTimeMap != null) {
			for (Map<Long, Long> idByTimeMap : stopCountByIdByTimeMap.values()) {
				total += idByTimeMap.values()
						.stream()
						.mapToLong((x) -> x)
						.sum();
			}
		}
		
		return total;
	}
	
}