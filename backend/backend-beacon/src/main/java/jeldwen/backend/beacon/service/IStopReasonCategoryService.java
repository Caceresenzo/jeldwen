package jeldwen.backend.beacon.service;

import jeldwen.backend.beacon.dto.StopReasonCategoryUpdateBody;
import jeldwen.backend.beacon.entity.StopReasonCategory;
import jeldwen.backend.beacon.model.descriptor.SimpleStopReasonCategoryDescriptor;
import jeldwen.backend.beacon.service.base.IModelBasedService;

public interface IStopReasonCategoryService extends IModelBasedService<StopReasonCategory, SimpleStopReasonCategoryDescriptor, StopReasonCategoryUpdateBody> {
	
}