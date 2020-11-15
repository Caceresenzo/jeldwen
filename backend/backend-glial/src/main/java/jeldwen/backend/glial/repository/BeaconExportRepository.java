package jeldwen.backend.glial.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jeldwen.backend.glial.entity.BeaconExport;
import jeldwen.backend.glial.repository.custom.BeaconExportRepositoryCustom;

public interface BeaconExportRepository extends JpaRepository<BeaconExport, Long>, BeaconExportRepositoryCustom {
	
	@Query("SELECT DISTINCT machine FROM BeaconExport") // TODO Unstable?
	List<String> findDistinctMachine();

	@Query("SELECT DISTINCT date FROM BeaconExport") // TODO Unstable?
	List<LocalDate> findDistinctDate();
	
	@Query("SELECT DISTINCT date FROM BeaconExport WHERE machine = :machine") // TODO Unstable?
	List<LocalDate> findDistinctDateByMachine(@Param("machine") String machine);
	
	List<BeaconExport> findAllByDate(LocalDate date);
	
	List<BeaconExport> findAllByMachine(String machine);
	
	List<BeaconExport> findAllByDateAndMachine(LocalDate date, String machine);
	
	long countByDateIn(List<LocalDate> dates);
	
	long countByDateInAndMachine(List<LocalDate> dates, String machine);
	
	void deleteAllByDateIn(List<LocalDate> dates);
	
	long deleteAllByDateInAndMachine(List<LocalDate> dates, String machine);
	
}