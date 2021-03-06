package jeldwen.backend.glial.convertor;

import java.time.Duration;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.stereotype.Component;

/**
 * {@link Duration} convertor for persistence.<br>
 * Allowing Hibernete to understand the shitty HH:mm:ss duration format.<br>
 * Since there is no way to property format and parse {@link Duration}, parsing is done through a split(':'); and then recomputed to reconstruct the {@link Duration} object.
 * 
 * @author Enzo CACERES
 */
@Converter
@Component
public class DurationConverter implements AttributeConverter<Duration, String> {
	
	/* Constants */
	public static final String SEPARATOR = ":";
	
	@Override
	public String convertToDatabaseColumn(Duration duration) {
		if (duration == null) {
			return null;
		}
		
		long seconds = duration.getSeconds();
		
		return String.format("%s:%s:%s", pad(seconds / 3600), pad((seconds % 3600) / 60), pad((seconds % 60)));
	}
	
	@Override
	public Duration convertToEntityAttribute(String raw) {
		if (raw == null || raw.isEmpty()) {
			return null;
		}
		
		String[] parts = raw.split(SEPARATOR);
		
		return Duration.ofSeconds((Long.parseLong(parts[0]) * 3600) + (Long.parseLong(parts[1]) * 60) + (Long.parseLong(parts[2])));
	}
	
	/**
	 * Pad the number to add a 0 if the number is less than 10.
	 * 
	 * @param n
	 *            Number to pad.
	 * @return Padded number.
	 */
	private String pad(long n) {
		return (n < 10 ? "0" : "") + n;
	}
	
}