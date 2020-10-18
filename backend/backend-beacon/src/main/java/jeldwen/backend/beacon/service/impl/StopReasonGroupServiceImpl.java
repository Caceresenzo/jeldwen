package jeldwen.backend.beacon.service.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import jeldwen.backend.beacon.dto.StopReasonGroupUpdateBody;
import jeldwen.backend.beacon.entity.StopReason;
import jeldwen.backend.beacon.entity.StopReasonGroup;
import jeldwen.backend.beacon.model.descriptor.SimpleStopReasonGroupDescriptor;
import jeldwen.backend.beacon.repository.StopReasonGroupRepository;
import jeldwen.backend.beacon.repository.StopReasonRepository;
import jeldwen.backend.beacon.service.IStopReasonGroupService;
import jeldwen.backend.beacon.service.IStopReasonService;
import jeldwen.backend.common.util.TypeAwareMapper;

@Service
public class StopReasonGroupServiceImpl implements IStopReasonGroupService {
	
	@Autowired
	private StopReasonGroupRepository repository;
	
	@Autowired
	private StopReasonRepository stopReasonRepository;
	
	@Autowired
	private IStopReasonService stopReasonService;
	
	/* Mappers */
	private final TypeAwareMapper<StopReasonGroup, SimpleStopReasonGroupDescriptor> mapper;
	
	/* Constructor */
	public StopReasonGroupServiceImpl() {
		this.mapper = new TypeAwareMapper<StopReasonGroup, SimpleStopReasonGroupDescriptor>() {
		};
	}
	
	@Override
	public Collection<StopReason> listReasons(long groupId) {
		Optional<StopReasonGroup> optional = repository.findById(groupId);
		
		if (optional.isPresent()) {
			return optional.get().getChildren();
		}
		
		return null;
	}
	
	@Override
	public StopReasonGroup create(StopReasonGroupUpdateBody body) {
		Set<StopReason> children = stopReasonService.listAllByIds(body.getChildrenIds())
				.stream()
				.filter(StopReason::isFree)
				.collect(Collectors.toSet());
		
		StopReasonGroup group = repository.save(new StopReasonGroup()
				.setName(body.getName())
				.setChildren(children));
		
		children.forEach((stopReason) -> stopReason.setGroup(group));
		
		stopReasonRepository.saveAll(children);
		
		return group;
	}
	
	@Override
	public StopReasonGroup update(long id, StopReasonGroupUpdateBody body) {
		Optional<StopReasonGroup> optional = repository.findById(id);
		
		if (optional.isPresent()) {
			StopReasonGroup group = optional.get();
			
			return repository.save(group
					.setName(body.getName())
					.setChildren(stopReasonService.saveAsParent(stopReasonService.listAllByIds(body.getChildrenIds()), group, IStopReasonService.CollisionResolution.IGNORE)));
		}
		
		return null;
	}
	
	@Override
	public StopReasonGroup delete(long id) {
		throw new NotYetImplementedException();
	}
	
	@Override
	public TypeAwareMapper<StopReasonGroup, SimpleStopReasonGroupDescriptor> getMapper() {
		return mapper;
	}
	
	@Override
	public JpaRepository<StopReasonGroup, Long> getRepository() {
		return repository;
	}
	
}