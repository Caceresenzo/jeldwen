package jeldwen.backend.beacon.service;

import java.util.List;

import jeldwen.backend.beacon.entity.ProductFamily;
import jeldwen.backend.beacon.model.descriptor.SimpleProductFamilyDescriptor;

public interface IProductFamilyService {
	
	List<SimpleProductFamilyDescriptor> listAll();
	
	ProductFamily find(long id);
	
	List<ProductFamily> listAllByIds(List<Long> ids);
	
}