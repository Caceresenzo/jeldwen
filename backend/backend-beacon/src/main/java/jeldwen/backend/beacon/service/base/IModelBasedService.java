package jeldwen.backend.beacon.service.base;

import java.util.List;

public interface IModelBasedService<M, D, B> {
	
	List<D> listAll();
	
	M find(long id);
	
	List<M> listAllByIds(List<Long> ids);
	
	M create(B body);
	
	M update(long id, B body);
	
	M delete(long id);
	
}