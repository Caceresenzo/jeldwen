package jeldwen.backend.beacon.service.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jeldwen.backend.common.util.TypeAwareMapper;

public interface IModelBasedService<E, D, B> {
	
	default List<D> listAll() {
		return getMapper().toDtos(getRepository().findAll());
	}
	
	default E find(long id) {
		return getRepository().findById(id).orElse(null);
	}
	
	default List<E> listAllByIds(List<Long> ids) {
		return getRepository().findAllById(ids);
	}
	
	E create(B body);
	
	E update(long id, B body);
	
	E delete(long id);
	
	default D toDto(E entity) {
		return getMapper().toDto(entity);
	}
	
	TypeAwareMapper<E, D> getMapper();
	
	JpaRepository<E, Long> getRepository();
	
}