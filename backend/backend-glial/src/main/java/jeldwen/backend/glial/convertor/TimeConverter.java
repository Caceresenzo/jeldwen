package jeldwen.backend.glial.convertor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.stereotype.Component;

/**
 * {@link LocalTime} convertor for persistence.<br>
 * Allowing Hibernete to understand the shitty HH:mm:ss time format.
 * 
 * @author Enzo CACERES
 */
@Converter
@Component
public class TimeConverter implements AttributeConverter<LocalTime, String> {
	
	/* Constants */
	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	@Override
	public String convertToDatabaseColumn(LocalTime time) {
		if (time == null) {
			return null;
		}
		
		return FORMATTER.format(time);
	}
	
	@Override
	public LocalTime convertToEntityAttribute(String raw) {
		if (raw == null || raw.isEmpty()) {
			return null;
		}
		
		return LocalTime.parse(raw, FORMATTER);
	}
	
}