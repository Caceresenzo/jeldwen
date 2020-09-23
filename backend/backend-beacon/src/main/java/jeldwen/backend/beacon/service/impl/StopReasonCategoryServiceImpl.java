package jeldwen.backend.beacon.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jeldwen.backend.beacon.dto.StopReasonCategoryUpdateBody;
import jeldwen.backend.beacon.entity.StopReasonCategory;
import jeldwen.backend.beacon.model.descriptor.SimpleStopReasonCategoryDescriptor;
import jeldwen.backend.beacon.repository.StopReasonCategoryRepository;
import jeldwen.backend.beacon.service.IStopReasonCategoryService;
import jeldwen.backend.common.util.TypeAwareMapper;

@Service
public class StopReasonCategoryServiceImpl implements IStopReasonCategoryService {
	
	@Autowired
	private StopReasonCategoryRepository stopReasonCategoryRepository;
	
	/* Mappers */
	private final TypeAwareMapper<StopReasonCategory, SimpleStopReasonCategoryDescriptor> simpleStopReasonCategoryDescriptorMapper;
	
	/* Constructor */
	public StopReasonCategoryServiceImpl() {
		this.simpleStopReasonCategoryDescriptorMapper = new TypeAwareMapper<StopReasonCategory, SimpleStopReasonCategoryDescriptor>() {
		};
	}
	
	@Override
	public List<SimpleStopReasonCategoryDescriptor> listAll() {
		return simpleStopReasonCategoryDescriptorMapper.toDtos(stopReasonCategoryRepository.findAll());
	}
	
	@Override
	public StopReasonCategory find(long id) {
		return stopReasonCategoryRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<StopReasonCategory> listAllByIds(List<Long> ids) {
		return stopReasonCategoryRepository.findAllById(ids);
	}
	
	@Override
	public StopReasonCategory create(StopReasonCategoryUpdateBody body) {
		return stopReasonCategoryRepository.save(new StopReasonCategory()
				.setName(body.getName())
				.setColor(body.getColor()));
	}
	
	@Override
	public StopReasonCategory update(long id, StopReasonCategoryUpdateBody body) {
		Optional<StopReasonCategory> optional = stopReasonCategoryRepository.findById(id);
		
		if (optional.isPresent()) {
			return stopReasonCategoryRepository.save(optional.get()
					.setName(body.getName())
					.setColor(body.getColor()));
		}
		
		return null;
	}
	
}