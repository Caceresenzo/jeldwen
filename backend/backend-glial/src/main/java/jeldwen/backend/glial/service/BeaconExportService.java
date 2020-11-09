package jeldwen.backend.glial.service;

import java.time.LocalDate;
import java.util.List;

import jeldwen.backend.glial.entity.BeaconExport;

public interface BeaconExportService {
	
	List<String> listMachines();
	
	List<LocalDate> listDates();
	
	List<LocalDate> listDates(String machine);
	
	List<BeaconExport> list(LocalDate date);
	
	List<BeaconExport> list(LocalDate date, String machine);
	
	String csv(LocalDate date);
	
	String csv(LocalDate date, String machine);
	
}