package jeldwen.backend.glial.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import jeldwen.backend.glial.convertor.DateConverter;
import jeldwen.backend.glial.entity.BeaconExport;
import jeldwen.backend.glial.repository.BeaconExportRepository;
import jeldwen.backend.glial.service.BeaconExportService;
import lombok.SneakyThrows;

@Service
public class BeaconExportServiceImpl implements BeaconExportService {
	
	@Autowired
	private BeaconExportRepository repository;
	
	@Autowired
	private DateConverter dateConverter;
	
	@Autowired
	private CsvMapper csvMapper;
	
	@Override
	public List<String> listMachines() {
		return repository.findDistinctMachine();
	}
	
	@Override
	public List<LocalDate> listDates(String machine) {
		return repository.findDistinctDateByMachine(machine).stream().map(dateConverter::convertToEntityAttribute).collect(Collectors.toList());
	}
	
	@Override
	public List<BeaconExport> list(LocalDate date) {
		if (date != null) {
			return repository.findAllByDate(date);
		}
		
		return repository.findAll();
	}
	
	@Override
	public List<BeaconExport> list(LocalDate date, String machine) {
		if (date != null) {
			return repository.findAllByDateAndMachine(date, machine);
		}
		
		return repository.findAllByMachine(machine);
	}
	
	@Override
	public String csv(LocalDate date) {
		return csv(list(date));
	}
	
	@Override
	public String csv(LocalDate date, String machine) {
		return csv(list(date, machine));
	}
	
	@SneakyThrows
	private String csv(List<BeaconExport> entries) {
		return csvMapper.writer(csvMapper.schemaFor(BeaconExport.class).withHeader()).writeValueAsString(entries);
	}
	
}