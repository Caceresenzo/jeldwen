package jeldwen.backend.beacon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jeldwen.backend.beacon.entity.Beacon;

@Repository
public interface BeaconRepository extends JpaRepository<Beacon, Long> {
	
}