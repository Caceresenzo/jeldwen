package jeldwen.backend.common.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import lombok.Data;

@Data
public class DateRange implements Iterable<LocalDate> {
	
	/* Variables */
	private final LocalDate startDate;
	private final LocalDate endDate;
	
	/* Constructor */
	public DateRange(LocalDate startDate, LocalDate endDate) {
		this.startDate = Objects.requireNonNull(startDate, "startDate == null");
		this.endDate = Objects.requireNonNull(endDate, "endDate == null");
		
		if (!endDate.equals(startDate) && endDate.isBefore(startDate)) {
			throw new IllegalArgumentException("start < end");
		}
	}
	
	@Override
	public Iterator<LocalDate> iterator() {
		return stream().iterator();
	}
	
	public Stream<LocalDate> stream() {
		return Stream.iterate(startDate, d -> d.plusDays(1))
				.limit(ChronoUnit.DAYS.between(startDate, endDate) + 1);
	}
	
	public List<LocalDate> toList() {
		List<LocalDate> dates = new ArrayList<>();
		
		for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
			dates.add(date);
		}
		
		return dates;
	}
	
}