package jeldwen.backend.glial.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jeldwen.backend.glial.entity.BeaconURL;
import jeldwen.backend.glial.repository.BeaconURLRepository;
import jeldwen.backend.glial.service.BeaconExportService;
import jeldwen.backend.glial.service.BeaconURLService;

@Service
public class BeaconURLServiceImpl implements BeaconURLService {
	
	@Autowired
	private BeaconURLRepository repository;
	
	@Autowired
	private BeaconExportService beaconExportService;
	
	@Override
	public List<BeaconURL> all() {
		return repository.findAll();
	}
	
	@Override
	public List<BeaconURL> listMachines() {
		Map<String, BeaconURL> byMachine = all()
				.stream()
				.collect(Collectors.toMap(BeaconURL::getMachine, Function.identity()));
		
		return beaconExportService.listMachines()
				.stream()
				.map((machine) -> Optional
						.ofNullable(byMachine.remove(machine))
						.orElseGet(() -> new BeaconURL()
								.setMachine(machine)))
				.collect(Collectors.toList());
	}
	
	@Override
	public BeaconURL set(String machine, String url) {
		return repository.save(repository
				.findByMachine(machine)
				.orElseGet(() -> new BeaconURL()
						.setMachine(machine))
				.setUrl(url));
	}
	
	@Transactional
	@Override
	public void delete(String machine) {
		repository.deleteByMachine(machine);
	}
	
}