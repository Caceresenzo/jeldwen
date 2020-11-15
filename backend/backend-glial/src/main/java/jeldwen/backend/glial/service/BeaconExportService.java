package jeldwen.backend.glial.service;

import java.time.LocalDate;
import java.util.List;

import jeldwen.backend.glial.entity.BeaconExport;

public interface BeaconExportService {
	
	/** @return A list of all unique machine name available. */
	List<String> listMachines();
	
	/** @return A list of all unique date available. */
	List<LocalDate> listDates();
	
	/** @return A list of all unique date available for a specified <code>machine</code>. */
	List<LocalDate> listDates(String machine);
	
	/**
	 * List beacon entries for a specified date and a specified amount of last n hour. (both of them can be <code>null</code>)
	 * 
	 * @param date
	 *            Specified date.
	 * @param lastNHour
	 *            Specified last n hour.
	 * @return A list of filtered beacon entries.
	 */
	List<BeaconExport> list(LocalDate date, Long lastNHour);
	
	/**
	 * List beacon entries for a specified date, a specified amount of last n hour and a specified machine name. (both of them can be <code>null</code>)
	 * 
	 * @param date
	 *            Specified date.
	 * @param lastNHour
	 *            Specified last n hour.
	 * @param machine
	 *            Specified machine.
	 * @return A list of filtered beacon entries.
	 */
	List<BeaconExport> list(LocalDate date, Long lastNHour, String machine);
	
	/**
	 * Export a CSV.
	 * 
	 * @param date
	 *            Specified date.
	 * @param lastNHour
	 *            Specified last n hour.
	 * @return A formatted CSV.
	 */
	String csv(LocalDate date, Long lastNHour);
	
	/**
	 * Export a CSV.
	 * 
	 * @param date
	 *            Specified date.
	 * @param lastNHour
	 *            Specified last n hour.
	 * @param machine
	 *            Specified machine.
	 * @return A formatted CSV.
	 */
	String csv(LocalDate date, Long lastNHour, String machine);

	long previewDelete(List<LocalDate> dates);

	long previewDelete(List<LocalDate> dates, String machine);

	void delete(List<LocalDate> dates);

	void delete(List<LocalDate> dates, String machine);
	
}