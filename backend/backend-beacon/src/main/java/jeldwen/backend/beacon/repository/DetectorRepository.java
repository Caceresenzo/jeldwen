package jeldwen.backend.beacon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jeldwen.backend.beacon.entity.Detector;

@Repository
public interface DetectorRepository extends JpaRepository<Detector, Long> {
	
	Optional<Detector> findByUnique(String unique);
	
}