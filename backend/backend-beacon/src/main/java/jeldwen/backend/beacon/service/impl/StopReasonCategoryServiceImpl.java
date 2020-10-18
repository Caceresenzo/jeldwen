package jeldwen.backend.beacon.service.impl;

import java.util.Optional;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
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
	private StopReasonCategoryRepository repository;
	
	/* Mappers */
	private final TypeAwareMapper<StopReasonCategory, SimpleStopReasonCategoryDescriptor> mapper;
	
	/* Constructor */
	public StopReasonCategoryServiceImpl() {
		this.mapper = new TypeAwareMapper<StopReasonCategory, SimpleStopReasonCategoryDescriptor>() {
		};
	}
	
	@Override
	public StopReasonCategory create(StopReasonCategoryUpdateBody body) {
		return repository.save(new StopReasonCategory()
				.setName(body.getName())
				.setColor(body.getColor()));
	}
	
	@Override
	public StopReasonCategory update(long id, StopReasonCategoryUpdateBody body) {
		Optional<StopReasonCategory> optional = repository.findById(id);
		
		if (optional.isPresent()) {
			return repository.save(optional.get()
					.setName(body.getName())
					.setColor(body.getColor()));
		}
		
		return null;
	}
	
	@Override
	public StopReasonCategory delete(long id) {
		throw new NotYetImplementedException();
	}
	
	@Override
	public TypeAwareMapper<StopReasonCategory, SimpleStopReasonCategoryDescriptor> getMapper() {
		return mapper;
	}
	
	@Override
	public JpaRepository<StopReasonCategory, Long> getRepository() {
		return repository;
	}
	
}