package jeldwen.backend.beacon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.backend.beacon.entity.StopReasonCategory;
import jeldwen.backend.beacon.repository.BeaconRepository;
import jeldwen.backend.beacon.service.IBeaconService;
import jeldwen.beacon.message.model.config.BeaconConfig;
import jeldwen.beacon.message.model.config.ProductFamilyConfig;
import jeldwen.beacon.message.model.config.StopReasonCategoryConfig;
import jeldwen.beacon.message.model.config.StopReasonConfig;

@Service
public class BeaconServiceImpl implements IBeaconService {
	
	@Autowired
	private BeaconRepository beaconRepository;
	
	@Override
	public List<Beacon> listAll() {
		return beaconRepository.findAll();
	}
	
	@Override
	public Beacon find(long id) {
		return beaconRepository.findById(id).orElse(null);
	}
	
	@Override
	public boolean create(String unique) {
		if (beaconRepository.existsByUnique(unique)) {
			return false;
		}
		
		return beaconRepository.save(new Beacon().setUnique(unique)) != null;
	}
	
	@Transactional
	@Override
	public BeaconConfig getConfig(String unique) {
		Optional<Beacon> optional = beaconRepository.findByUnique(unique);
		
		if (optional.isPresent()) {
			Beacon beacon = optional.get();
			
			Map<Long, StopReasonCategory> categoryToIdMap = new HashMap<>();
			ToLongFunction<StopReasonCategory> categoryToId = (category) -> {
				if (category == null) {
					return 0l;
				}
				
				return categoryToIdMap.computeIfAbsent(category.getId(), (key) -> category).getId();
			};
			
			return new BeaconConfig()
					.setName(beacon.getName())
					.setStopReasons(Stream.concat(
							beacon.getStopReasonGroups()
									.stream()
									.flatMap((group) -> group.getChildren()
											.stream()),
							beacon.getStopReasons()
									.stream())
							.map((stopReason) -> new StopReasonConfig()
									.setId(stopReason.getId())
									.setName(stopReason.getName())
									.setCategoryId(categoryToId.applyAsLong(stopReason.getCategory())))
							.collect(Collectors.toList()))
					.setStopReasonCategories(categoryToIdMap.values()
							.stream()
							.map((category) -> new StopReasonCategoryConfig()
									.setId(category.getId())
									.setName(category.getName())
									.setColor(category.getName()))
							.collect(Collectors.toList()))
					.setProductFamilies(beacon.getProductFamilies()
							.stream()
							.map((family) -> new ProductFamilyConfig()
									.setId(family.getId())
									.setName(family.getName())
									.setCycleTime(family.getCycleTime()))
							.collect(Collectors.toList()))
					.setSyntheticPerformanceRateThreshold(beacon.getSyntheticPerformanceRateThreshold());
		}
		
		return null;
	}
	
}