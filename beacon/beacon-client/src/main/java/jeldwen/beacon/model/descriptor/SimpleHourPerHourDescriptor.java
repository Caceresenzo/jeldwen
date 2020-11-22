package jeldwen.beacon.model.descriptor;

import java.time.LocalDate;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SimpleHourPerHourDescriptor implements Comparable<SimpleHourPerHourDescriptor> {

	private LocalDate date;
	private long hour;
	private long open;
	private long stop;
	private long produced;
	private long objective;
	
	public double getPercent() {
		return ((double) produced + 1) / ((double) objective + 1);
	}

	@Override
	public int compareTo(SimpleHourPerHourDescriptor other) {
		int diff = date.compareTo(other.getDate());
		
		if (diff == 0) {
			return Long.compare(hour, other.getHour());
		}
		
		return diff;
	}
	
}