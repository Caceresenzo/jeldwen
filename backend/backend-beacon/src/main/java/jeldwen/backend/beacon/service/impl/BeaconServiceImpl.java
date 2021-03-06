package jeldwen.backend.beacon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import jeldwen.backend.beacon.dto.BeaconUpdateBody;
import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.backend.beacon.entity.StopReasonCategory;
import jeldwen.backend.beacon.model.descriptor.SimpleBeaconDescriptor;
import jeldwen.backend.beacon.repository.BeaconRepository;
import jeldwen.backend.beacon.service.IBeaconClientService;
import jeldwen.backend.beacon.service.IBeaconService;
import jeldwen.backend.beacon.service.IProductFamilyService;
import jeldwen.backend.beacon.service.IStopReasonGroupService;
import jeldwen.backend.beacon.service.IStopReasonService;
import jeldwen.backend.common.util.TypeAwareMapper;
import jeldwen.beacon.message.model.config.BeaconConfig;
import jeldwen.beacon.message.model.config.ProductFamilyConfig;
import jeldwen.beacon.message.model.config.StopReasonCategoryConfig;
import jeldwen.beacon.message.model.config.StopReasonConfig;

@Service
public class BeaconServiceImpl implements IBeaconService {
	
	@Autowired
	private BeaconRepository repository;
	
	@Autowired
	private IProductFamilyService productFamilyService;
	
	@Autowired
	private IStopReasonGroupService stopReasonGroupService;
	
	@Autowired
	private IStopReasonService stopReasonService;
	
	@Autowired
	private IBeaconClientService beaconClientService;
	
	/* Mappers */
	private final TypeAwareMapper<Beacon, SimpleBeaconDescriptor> mapper;

	/* Constructor */
	public BeaconServiceImpl() {
		this.mapper = new TypeAwareMapper<Beacon, SimpleBeaconDescriptor>() {
		};
	}
	
	@Override
	public Beacon find(String unique) {
		return repository.findByUnique(unique).orElse(null);
	}
	
	@Override
	public List<Beacon> listAllByIds(List<Long> ids) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean create(String unique) {
		if (repository.existsByUnique(unique)) {
			return false;
		}
		
		return repository.save(new Beacon().setUnique(unique)) != null;
	}
	
	@Override
	public Beacon update(long id, BeaconUpdateBody body) {
		Optional<Beacon> optional = repository.findById(id);
		
		if (optional.isPresent()) {
			Beacon beacon = optional.get();
			
			return repository.save(beacon
					.setName(body.getName())
					.setProductFamilies(productFamilyService.listAllByIds(body.getProductFamilyIds()))
					.setStopReasonGroups(stopReasonGroupService.listAllByIds(body.getStopReasonGroupIds()))
					.setStopReasons(stopReasonService.saveAsParent(stopReasonService.listAllByIds(body.getStopReasonIds()), beacon, IStopReasonService.CollisionResolution.IGNORE))
					.setSyntheticPerformanceRateThreshold(body.getSyntheticPerformanceRateThreshold()));
		}
		
		return null;
	}
	
	@Transactional
	@Override
	public BeaconConfig getConfig(String unique) {
		Optional<Beacon> optional = repository.findByUnique(unique);
		
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
									.setColor(category.getColor()))
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
	
	@Override
	public Beacon create(BeaconUpdateBody body) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Beacon delete(long id) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public TypeAwareMapper<Beacon, SimpleBeaconDescriptor> getMapper() {
		return mapper;
	}
	
	@Override
	public JpaRepository<Beacon, Long> getRepository() {
		return repository;
	}
	
	@Override
	public Boolean reconfigure(long id) {
		return delegateToBeaconClientService(id, beaconClientService::reconfigure);
	}
	
	@Override
	public Boolean forceTrigger(long id) {
		return delegateToBeaconClientService(id, beaconClientService::forceTrigger);
	}
	
	private Boolean delegateToBeaconClientService(long id, Predicate<String> function) {
		Optional<Beacon> optional = repository.findById(id);
		
		if (optional.isPresent()) {
			Beacon beacon = optional.get();
			
			return function.test(beacon.getUnique());
		}
		
		return null;
	}
	
}