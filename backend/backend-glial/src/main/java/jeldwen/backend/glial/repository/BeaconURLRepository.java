package jeldwen.backend.glial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jeldwen.backend.glial.entity.BeaconURL;

@Repository
public interface BeaconURLRepository extends JpaRepository<BeaconURL, Long> {
	
	Optional<BeaconURL> findByMachine(String machine);
	
	void deleteByMachine(String machine);
	
}