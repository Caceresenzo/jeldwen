package jeldwen.backend.beacon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jeldwen.backend.beacon.entity.ProductFamily;

@Repository
public interface ProductFamilyRepository extends JpaRepository<ProductFamily, Long> {
	
	List<ProductFamily> findByIdIn(List<Long> ids);
	
}