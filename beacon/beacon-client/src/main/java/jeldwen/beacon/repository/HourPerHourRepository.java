package jeldwen.beacon.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jeldwen.beacon.entity.HourPerHour;

public interface HourPerHourRepository extends JpaRepository<HourPerHour, Long> {
	
	Optional<HourPerHour> findByDateAndHour(LocalDate date, long hour);
	
}