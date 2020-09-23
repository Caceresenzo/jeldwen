package jeldwen.backend.beacon.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
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
	private StopReasonGroupRepository stopReasonGroupRepository;
	
	@Autowired
	private StopReasonRepository stopReasonRepository;
	
	@Autowired
	private IStopReasonService stopReasonService;
	
	/* Mappers */
	private final TypeAwareMapper<StopReasonGroup, SimpleStopReasonGroupDescriptor> simpleStopReasonGroupDescriptorMapper;
	
	/* Constructor */
	public StopReasonGroupServiceImpl() {
		this.simpleStopReasonGroupDescriptorMapper = new TypeAwareMapper<StopReasonGroup, SimpleStopReasonGroupDescriptor>() {
		};
	}
	
	@Override
	public List<SimpleStopReasonGroupDescriptor> listAll() {
		return simpleStopReasonGroupDescriptorMapper.toDtos(stopReasonGroupRepository.findAll());
	}
	
	@Override
	public StopReasonGroup find(long id) {
		return stopReasonGroupRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<StopReason> listReasons(long groupId) {
		Optional<StopReasonGroup> optional = stopReasonGroupRepository.findById(groupId);
		
		if (optional.isPresent()) {
			return optional.get().getChildren();
		}
		
		return null;
	}
	
	@Override
	public List<StopReasonGroup> listAllByIds(List<Long> ids) {
		return stopReasonGroupRepository.findAllById(ids);
	}
	
	@Override
	public StopReasonGroup create(StopReasonGroupUpdateBody body) {
		return stopReasonGroupRepository.save(new StopReasonGroup()
				.setName(body.getName())
				.setChildren(stopReasonRepository.saveAll(stopReasonService.listAllByIds(body.getChildrenIds())
						.stream()
						.filter(((Predicate<StopReason>) StopReason::isAttached).negate())
						.map(StopReason::attach)
						.collect(Collectors.toList()))));
	}
	
	@Override
	public StopReasonGroup update(long id, StopReasonGroupUpdateBody body) {
		throw new NotYetImplementedException();
	}
	
}