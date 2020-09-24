package jeldwen.backend.beacon.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import jeldwen.backend.beacon.dto.StopReasonUpdateBody;
import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.backend.beacon.entity.StopReason;
import jeldwen.backend.beacon.entity.StopReasonCategory;
import jeldwen.backend.beacon.entity.StopReasonGroup;
import jeldwen.backend.beacon.entity.interfaces.IStopReasonParent;
import jeldwen.backend.beacon.model.descriptor.SimpleStopReasonDescriptor;
import jeldwen.backend.beacon.repository.BeaconRepository;
import jeldwen.backend.beacon.repository.StopReasonGroupRepository;
import jeldwen.backend.beacon.repository.StopReasonRepository;
import jeldwen.backend.beacon.service.IBeaconService;
import jeldwen.backend.beacon.service.IStopReasonCategoryService;
import jeldwen.backend.beacon.service.IStopReasonGroupService;
import jeldwen.backend.beacon.service.IStopReasonService;
import jeldwen.backend.common.util.TypeAwareMapper;

@Service
public class StopReasonServiceImpl implements IStopReasonService {
	
	@Autowired
	private StopReasonRepository stopReasonRepository;
	
	@Autowired
	private StopReasonGroupRepository stopReasonGroupRepository;
	
	@Autowired
	private BeaconRepository beaconRepository;
	
	@Autowired
	private IStopReasonCategoryService stopReasonCategoryService;
	
	@Autowired
	private IStopReasonGroupService stopReasonGroupService;
	
	@Autowired
	private IBeaconService beaconService;
	
	/* Mappers */
	private final TypeAwareMapper<StopReason, SimpleStopReasonDescriptor> simpleStopReasonDescriptorMapper;
	
	/* Constructor */
	public StopReasonServiceImpl() {
		this.simpleStopReasonDescriptorMapper = new TypeAwareMapper<StopReason, SimpleStopReasonDescriptor>() {
		};
		
		simpleStopReasonDescriptorMapper.getWrappedModelMapper().getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Override
	public List<SimpleStopReasonDescriptor> listAll() {
		return simpleStopReasonDescriptorMapper.toDtos(stopReasonRepository.findAll());
	}
	
	@Override
	public StopReason find(long id) {
		return stopReasonRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<SimpleStopReasonDescriptor> listAll(boolean includeFree, Inclusion includeParent, Long parentId, boolean ignoreParent) {
		List<StopReason> stopReasons;
		
		if (ignoreParent) {
			stopReasons = stopReasonRepository.findAll();
		} else {
			stopReasons = new ArrayList<>();
			
			if (includeFree) {
				stopReasons.addAll(stopReasonRepository.findAllByGroupIsNullAndBeaconIsNull());
			}
			
			if (includeParent != null && parentId != null) {
				if (Inclusion.BEACON.equals(includeParent)) {
					Beacon beacon = beaconService.find(parentId);
					
					if (beacon != null) {
						stopReasons.addAll(stopReasonRepository.findAllByBeaconIs(beacon));
					}
				} else if (Inclusion.GROUP.equals(includeParent)) {
					StopReasonGroup group = stopReasonGroupService.find(parentId);
					
					if (group != null) {
						stopReasons.addAll(stopReasonRepository.findAllByGroupIs(group));
					}
				}
			}
		}
		
		return simpleStopReasonDescriptorMapper.toDtos(stopReasons);
	}
	
	@Override
	public List<StopReason> list(long beaconId, boolean expandGroups) {
		Optional<Beacon> optional = beaconRepository.findById(beaconId);
		
		if (optional.isPresent()) {
			Beacon beacon = optional.get();
			
			List<StopReason> stopReasons = new ArrayList<>();
			stopReasons.addAll(beacon.getStopReasons());
			
			if (expandGroups) {
				for (StopReasonGroup group : beacon.getStopReasonGroups()) {
					stopReasons.addAll(group.getChildren());
				}
			}
			
			return stopReasons;
		}
		
		return null;
	}
	
	@Override
	public List<StopReason> listAllByIds(List<Long> ids) {
		return stopReasonRepository.findAllById(ids);
	}
	
	@Override
	public StopReason create(StopReasonUpdateBody body) {
		StopReasonCategory category = stopReasonCategoryService.find(body.getCategoryId());
		
		if (category == null) {
			throw new EntityNotFoundException();
		}
		
		return stopReasonRepository.save(new StopReason()
				.setName(body.getName())
				.setCategory(category));
	}
	
	@Override
	public StopReason update(long id, StopReasonUpdateBody body) {
		Optional<StopReason> optional = stopReasonRepository.findById(id);
		
		if (optional.isPresent()) {
			StopReasonCategory category = stopReasonCategoryService.find(body.getCategoryId());
			
			if (category == null) {
				throw new EntityNotFoundException();
			}
			
			return stopReasonRepository.save(optional.get()
					.setName(body.getName())
					.setCategory(category));
		}
		
		return null;
	}
	
	@Override
	public StopReason ungroup(long id) {
		Optional<StopReason> optional = stopReasonRepository.findById(id);
		
		if (optional.isPresent()) {
			StopReason stopReason = optional.get();
			StopReasonGroup group = stopReason.getGroup();
			
			if (group != null) {
				group.getChildren().remove(stopReason);
				
				stopReasonGroupRepository.save(group);
				return stopReasonRepository.save(stopReason.setGroup(null));
			}
		}
		
		return null;
	}
	
	@Override
	public StopReason delete(long id) {
		return null;
	}
	
	@Override
	public Set<StopReason> saveAsParent(List<StopReason> stopReasons, Beacon beacon, CollisionResolution ifCollision) {
		return doSaveAsParent(stopReasons, beacon, ifCollision);
	}
	
	@Override
	public Set<StopReason> saveAsParent(List<StopReason> stopReasons, StopReasonGroup group, CollisionResolution ifCollision) {
		return doSaveAsParent(stopReasons, group, ifCollision);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Set<StopReason> doSaveAsParent(List<StopReason> stopReasons, IStopReasonParent toParent, CollisionResolution ifCollision) {
		Map<Class<? extends IStopReasonParent>, Map<IStopReasonParent, List<StopReason>>> parentClassToEntriesMap = new HashMap<>();
		
		BiConsumer<IStopReasonParent, StopReason> insertMap = (parent, stopReason) -> {
			parentClassToEntriesMap.computeIfAbsent(parent.getClass(), (key) -> new HashMap<>()).computeIfAbsent(parent, (key) -> new ArrayList<>()).add(stopReason);
		};
		
		Set<StopReason> kept;
		
		if (CollisionResolution.UNGROUP_BEFORE.equals(ifCollision)) {
			for (StopReason stopReason : stopReasons) {
				IStopReasonParent parent = stopReason.getParent();
				
				if (!toParent.equals(parent)) {
					insertMap.accept(parent, stopReason);
				}
			}
			
			kept = stopReasons.stream()
					.map((child) -> stopReasonRepository.save(child.setGroup(null)))
					.collect(Collectors.toSet());
		} else {
			kept = stopReasons.stream()
					.filter((stopReason) -> stopReason.isFree() || toParent.equals(stopReason.getParent()))
					.collect(Collectors.toSet());
		}
		
		Set<StopReason> left = new HashSet<>(toParent.getStopReasonChildren());
		left.removeAll(kept);
		
		left.forEach((stopReason) -> {
			insertMap.accept(stopReason.getParent(), stopReason);
		});
		
		for (Entry<Class<? extends IStopReasonParent>, Map<IStopReasonParent, List<StopReason>>> modelEntry : parentClassToEntriesMap.entrySet()) {
			JpaRepository repository = modelClassToRepository(modelEntry.getKey());
			
			for (Entry<IStopReasonParent, List<StopReason>> entry : modelEntry.getValue().entrySet()) {
				IStopReasonParent parent = entry.getKey();
				List<StopReason> reasons = entry.getValue();
				
				parent.getStopReasonChildren().removeAll(reasons);
				
				repository.save(parent);
				
				reasons.forEach((stopReason) -> stopReasonRepository.save(stopReason.setParent(null)));
			}
		}
		
		return kept.stream()
				.map((stopReason) -> stopReason.setParent(toParent))
				.map(stopReasonRepository::save)
				.collect(Collectors.toSet());
	}
	
	@SuppressWarnings({ "rawtypes" })
	private JpaRepository modelClassToRepository(Class clazz) {
		if (StopReasonGroup.class.equals(clazz)) {
			return stopReasonGroupRepository;
		} else if (Beacon.class.equals(clazz)) {
			return beaconRepository;
		} else {
			throw new IllegalStateException("unsupported model class: " + clazz);
		}
	}
	
}