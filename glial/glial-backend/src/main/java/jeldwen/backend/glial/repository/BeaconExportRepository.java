package jeldwen.backend.glial.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jeldwen.backend.glial.entity.BeaconExport;

public interface BeaconExportRepository extends JpaRepository<BeaconExport, Long> {
	
	@Query(nativeQuery = true, value = "SELECT DISTINCT `machine` FROM `export_balises_15min`") // TODO Unstable
	List<String> findDistinctMachine();

	@Query(nativeQuery = true, value = "SELECT DISTINCT `date` FROM `export_balises_15min`") // TODO Unstable
	List<String> findDistinctDate();
	
	@Query(nativeQuery = true, value = "SELECT DISTINCT `date` FROM `export_balises_15min` WHERE `machine` = :machine") // TODO Unstable
	List<String> findDistinctDateByMachine(@Param("machine") String machine);
	
	List<BeaconExport> findAllByDate(LocalDate date);
	
	List<BeaconExport> findAllByMachine(String machine);
	
	List<BeaconExport> findAllByDateAndMachine(LocalDate date, String machine);
	
}