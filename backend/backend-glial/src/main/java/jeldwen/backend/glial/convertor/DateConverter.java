package jeldwen.backend.glial.convertor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.stereotype.Component;

/**
 * {@link LocalDate} convertor for persistence.<br>
 * Allowing Hibernete to understand the shitty dd/MM/yyyy date format.
 * 
 * @author Enzo CACERES
 */
@Converter
@Component
public class DateConverter implements AttributeConverter<LocalDate, String> {
	
	/* Constants */
	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	@Override
	public String convertToDatabaseColumn(LocalDate date) {
		if (date == null) {
			return null;
		}
		
		return FORMATTER.format(date);
	}
	
	@Override
	public LocalDate convertToEntityAttribute(String raw) {
		if (raw == null || raw.isEmpty()) {
			return null;
		}
		
		return LocalDate.parse(raw, FORMATTER);
	}
	
}