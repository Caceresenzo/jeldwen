package jeldwen.backend.beacon.service;

import jeldwen.backend.beacon.dto.ProductFamilyUpdateBody;
import jeldwen.backend.beacon.entity.ProductFamily;
import jeldwen.backend.beacon.model.descriptor.SimpleProductFamilyDescriptor;
import jeldwen.backend.beacon.service.base.IModelBasedService;

public interface IProductFamilyService extends IModelBasedService<ProductFamily, SimpleProductFamilyDescriptor, ProductFamilyUpdateBody> {
	
}