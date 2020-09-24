package jeldwen.backend.beacon.service.impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
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
	private ProductFamilyRepository productFamilyRepository;
	
	/* Mappers */
	private final TypeAwareMapper<ProductFamily, SimpleProductFamilyDescriptor> simpleProductFamilyDescriptorMapper;
	
	public ProductFamilyServiceImpl() {
		this.simpleProductFamilyDescriptorMapper = new TypeAwareMapper<ProductFamily, SimpleProductFamilyDescriptor>() {
		};
	}
	
	@Override
	public List<SimpleProductFamilyDescriptor> listAll() {
		return simpleProductFamilyDescriptorMapper.toDtos(productFamilyRepository.findAll());
	}
	
	@Override
	public ProductFamily find(long id) {
		return productFamilyRepository.findById(id).orElse(null);
	}
	
	@Override
	public List<ProductFamily> listAllByIds(List<Long> ids) {
		return productFamilyRepository.findAllById(ids);
	}
	
	@Override
	public ProductFamily create(ProductFamilyUpdateBody body) {
		return productFamilyRepository.save(new ProductFamily()
				.setName(body.getName())
				.setCycleTime(body.getCycleTime()));
	}
	
	@Override
	public ProductFamily update(long id, ProductFamilyUpdateBody body) {
		Optional<ProductFamily> optional = productFamilyRepository.findById(id);
		
		if (optional.isPresent()) {
			return productFamilyRepository.save(optional.get()
					.setName(body.getName())
					.setCycleTime(body.getCycleTime()));
		}
		
		return null;
	}
	
	@Override
	public ProductFamily delete(long id) {
		throw new NotYetImplementedException();
	}
	
}