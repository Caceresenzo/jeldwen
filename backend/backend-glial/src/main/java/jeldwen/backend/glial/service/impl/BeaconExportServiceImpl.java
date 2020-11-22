package jeldwen.backend.glial.service.impl;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import jeldwen.backend.glial.entity.BeaconExport;
import jeldwen.backend.glial.repository.BeaconExportRepository;
import jeldwen.backend.glial.service.BeaconExportService;
import lombok.SneakyThrows;

@Service
public class BeaconExportServiceImpl implements BeaconExportService {
	
	@Autowired
	private BeaconExportRepository repository;
	
	@Autowired
	private CsvMapper csvMapper;
	
	@Override
	public List<String> listMachines() {
		return repository.findDistinctMachine();
	}
	
	@Override
	public List<LocalDate> listDates() {
		return repository.findDistinctDate();
	}
	
	@Override
	public List<LocalDate> listDates(String machine) {
		return repository.findDistinctDateByMachine(machine);
	}
	
	@Override
	public List<BeaconExport> list(LocalDate date, Long lastNHour) {
		return repository.findAll(date, lastNHour);
	}
	
	@Override
	public List<BeaconExport> list(LocalDate date, Long lastNHour, String machine) {
		return repository.findAll(date, lastNHour, machine);
	}
	
	@Override
	public String csv(LocalDate date, Long lastNHour) {
		return csv(list(date, lastNHour));
	}
	
	@Override
	public String csv(LocalDate date, Long lastNHour, String machine) {
		return csv(list(date, lastNHour, machine));
	}
	
	@SneakyThrows
	private String csv(List<BeaconExport> entries) {
		return csvMapper.writer(csvMapper.schemaFor(BeaconExport.class).withHeader()).writeValueAsString(entries);
	}

	@Override
	public long previewDelete(List<LocalDate> dates) {
		return repository.countByDateIn(dates);
	}

	@Override
	public long previewDelete(List<LocalDate> dates, String machine) {
		return repository.countByDateInAndMachine(dates, machine);
	}

	@Transactional
	@Override
	public void delete(List<LocalDate> dates) {
		repository.deleteAllByDateIn(dates);
	}

	@Transactional
	@Override
	public void delete(List<LocalDate> dates, String machine) {
		repository.deleteAllByDateInAndMachine(dates, machine);
	}
	
}