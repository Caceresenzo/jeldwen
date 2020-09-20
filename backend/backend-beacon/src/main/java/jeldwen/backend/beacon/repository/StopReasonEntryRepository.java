package jeldwen.backend.beacon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.backend.beacon.entity.StopReasonEntry;

@Repository
public interface StopReasonEntryRepository extends JpaRepository<StopReasonEntry, Long> {
	
	Optional<StopReasonEntry> findByKeyEqualsAndBeaconEquals(String key, Beacon beacon);
	
}