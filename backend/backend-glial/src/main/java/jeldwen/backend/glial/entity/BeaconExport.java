package jeldwen.backend.glial.entity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jeldwen.backend.common.persistance.duration.DurationAsSecondsSerializer;
import jeldwen.backend.glial.convertor.DateConverter;
import jeldwen.backend.glial.convertor.DurationConverter;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Beacon export entity class.<br>
 * Since I am 'forced' to adapt to what I am given, this class is flooded with custom converter and renaming. Which is not a problem really, but make everything ugly.
 * 
 * @author Enzo CACERES
 */
@Entity
@Table(name = "export_balises_15min")
@Data
@Accessors(chain = true)
@JsonPropertyOrder({ "date", "time", "machine", "produced", "objective", "yellowStop", "orangeStop", "pinkStop", "greenStop", "blueStop", "otherStop", "totalStop", "nonQualifiedStop" })
public class BeaconExport {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	
	@Column(name = "Date")
	@Convert(converter = DateConverter.class)
	private LocalDate date;
	
	@Column(name = "Heure")
	private LocalTime time;
	
	@Column(name = "Machine")
	private String machine;
	
	@Column(name = "Production")
	private long produced;
	
	@Column(name = "Objectif")
	private long objective;
	
	@Column(name = "Arret jaune")
	@Convert(converter = DurationConverter.class)
	@JsonSerialize(using = DurationAsSecondsSerializer.class)
	private Duration yellowStop;
	
	@Column(name = "Arret orange")
	@Convert(converter = DurationConverter.class)
	@JsonSerialize(using = DurationAsSecondsSerializer.class)
	private Duration orangeStop;
	
	@Column(name = "Arret rose")
	@Convert(converter = DurationConverter.class)
	@JsonSerialize(using = DurationAsSecondsSerializer.class)
	private Duration pinkStop;
	
	@Column(name = "Arret vert")
	@Convert(converter = DurationConverter.class)
	@JsonSerialize(using = DurationAsSecondsSerializer.class)
	private Duration greenStop;
	
	@Column(name = "Arret bleu")
	@Convert(converter = DurationConverter.class)
	@JsonSerialize(using = DurationAsSecondsSerializer.class)
	private Duration blueStop;
	
	@Column(name = "Arret autre")
	@Convert(converter = DurationConverter.class)
	@JsonSerialize(using = DurationAsSecondsSerializer.class)
	private Duration otherStop;
	
	@Column(name = "Total arret")
	@Convert(converter = DurationConverter.class)
	@JsonSerialize(using = DurationAsSecondsSerializer.class)
	private Duration totalStop;
	
	/** @return The sum of all the stops, only and only if all of them are not <code>null</code>, in which case a <code>null</code> value is returned. The value cannot be below 0. */
	@JsonSerialize(using = DurationAsSecondsSerializer.class)
	public Duration getNonQualifiedStop() {
		if (totalStop != null && yellowStop != null && orangeStop != null && pinkStop != null && greenStop != null && blueStop != null && otherStop != null) {
			return Duration.ofSeconds(Math.max(totalStop.minus(yellowStop.plus(orangeStop).plus(pinkStop).plus(greenStop).plus(blueStop).plus(otherStop)).getSeconds(), 0));
		}
		
		return null;
	}
	
	/** @return A {@link LocalDateTime} from the {@link LocalDate date} and {@link LocalTime time} of this beacon export. */
	public LocalDateTime toLocalDateTime() {
		return LocalDateTime.of(date, time);
	}
	
}