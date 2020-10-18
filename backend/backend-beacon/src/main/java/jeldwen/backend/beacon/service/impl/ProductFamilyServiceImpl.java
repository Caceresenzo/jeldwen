package jeldwen.backend.beacon.service.impl;

import java.util.Optional;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import jeldwen.backend.beacon.dto.ProductFamilyUpdateBody;
import jeldwen.backend.beacon.entity.ProductFamily;
import jeldwen.backend.beacon.model.descriptor.SimpleProductFamilyDescriptor;
import jeldwen.backend.beacon.repository.ProductFamilyRepository;
import jeldwen.backend.beacon.service.IProductFamilyService;
import jeldwen.backend.common.util.TypeAwareMapper;

@Service
public class ProductFamilyServiceImpl implements IProductFamilyService {
	
	@Autowired
	private ProductFamilyRepository repository;
	
	/* Mappers */
	private final TypeAwareMapper<ProductFamily, SimpleProductFamilyDescriptor> mapper;
	
	/* Constructor */
	public ProductFamilyServiceImpl() {
		this.mapper = new TypeAwareMapper<ProductFamily, SimpleProductFamilyDescriptor>() {
		};
	}
	
	@Override
	public ProductFamily create(ProductFamilyUpdateBody body) {
		return repository.save(new ProductFamily()
				.setName(body.getName())
				.setCycleTime(body.getCycleTime()));
	}
	
	@Override
	public ProductFamily update(long id, ProductFamilyUpdateBody body) {
		Optional<ProductFamily> optional = repository.findById(id);
		
		if (optional.isPresent()) {
			return repository.save(optional.get()
					.setName(body.getName())
					.setCycleTime(body.getCycleTime()));
		}
		
		return null;
	}
	
	@Override
	public ProductFamily delete(long id) {
		throw new NotYetImplementedException();
	}
	
	@Override
	public TypeAwareMapper<ProductFamily, SimpleProductFamilyDescriptor> getMapper() {
		return mapper;
	}
	
	@Override
	public JpaRepository<ProductFamily, Long> getRepository() {
		return repository;
	}
	
}