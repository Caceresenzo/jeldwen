package jeldwen.backend.glial.repository.custom;

import java.time.LocalDate;
import java.util.List;

import jeldwen.backend.glial.entity.BeaconExport;

public interface BeaconExportRepositoryCustom {
	
	/**
	 * Find all beacon export entries without a <code>machine</code> filter.<br>
	 * Depending on the filters, this function will return all of the matched beacon exports.<br>
	 * <br>
	 * 
	 * If a <code>date</code> is not null, every entries will be returned.<br>
	 * If a <code>lastNHour</code> is not null, every entries from either now, or specified <code>date</code>, to -n hour will be returned.<br>
	 * <br>
	 * 
	 * A typical usage will be:
	 * 
	 * <pre>
	 * // Return every entries.
	 * findAll(null, null);
	 * 
	 * // Return every entries with date January 01, 2001.
	 * findAll(LocalDate.of(2001, 01, 01), null);
	 * 
	 * // Return every entries for the last 5 hour, relative to now.
	 * findAll(null, 5);
	 * 
	 * // Return every entries for the last 5 hour, relative to January 01, 2001.
	 * findAll(LocalDate.of(2001, 01, 01), 5);
	 * </pre>
	 * 
	 * @param date
	 *            Specified date (can be <code>null</code>).
	 * @param lastNHour
	 *            Last n hour (can be <code>null</code>).
	 * @return Filtered beacon export entries.
	 * @see BeaconExportRepositoryCustom#findAll(LocalDate, Long, String) findAll(LocalDate, Long, String)
	 * @see LocalDate
	 */
	List<BeaconExport> findAll(LocalDate date, Long lastNHour);

	
	/**
	 * Find all beacon export entries without a <code>machine</code> filter.<br>
	 * Depending on the filters, this function will return all of the matched beacon exports.<br>
	 * <br>
	 * 
	 * If a <code>date</code> is not null, every entries with the specified date will be returned.<br>
	 * If a <code>lastNHour</code> is not null, every entries from either now, or specified <code>date</code>, to -n hour will be returned.<br>
	 * If a <code>machine</code> is not null, only entries with matching machine will be returned.<br>
	 * <br>
	 * 
	 * A typical usage will be (for more exemple, see {@link BeaconExportRepositoryCustom#findAll(LocalDate, Long) findAll(LocalDate, Long)}):
	 * 
	 * <pre>
	 * // Return every entries.
	 * findAll(null, null, null);
	 * 
	 * // Return every entries for the last 5 hour, relative to January 01, 2001, that a "painter" has generated.
	 * findAll(LocalDate.of(2001, 01, 01), 5, "painter");
	 * </pre>
	 * 
	 * @param date
	 *            Specified date (can be <code>null</code>).
	 * @param lastNHour
	 *            Last n hour (can be <code>null</code>).
	 * @param machine
	 *            A machine name (can be <code>null</code>).
	 * @return Filtered beacon export entries.
	 * @see BeaconExportRepositoryCustom#findAll(LocalDate, Long) findAll(LocalDate, Long)
	 * @see LocalDate
	 */
	List<BeaconExport> findAll(LocalDate date, Long lastNHour, String machine);
	
}