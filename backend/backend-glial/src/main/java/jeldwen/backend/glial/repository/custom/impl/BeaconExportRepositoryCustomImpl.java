package jeldwen.backend.glial.repository.custom.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import jeldwen.backend.common.util.DateRange;
import jeldwen.backend.glial.entity.BeaconExport;
import jeldwen.backend.glial.repository.custom.BeaconExportRepositoryCustom;

@Repository
public class BeaconExportRepositoryCustomImpl implements BeaconExportRepositoryCustom {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<BeaconExport> findAll(LocalDate date, Long lastNHour) {
		return findAll(date, lastNHour, null);
	}
	
	@Override
	public List<BeaconExport> findAll(LocalDate date, Long lastNHour, String machine) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<BeaconExport> query = builder.createQuery(BeaconExport.class);
		Root<BeaconExport> root = query.from(BeaconExport.class);
		
		Path<String> datePath = root.get("date");
		Path<String> machinePath = root.get("machine");
		
		LocalDateTime top;
		LocalDateTime bottom;
		
		List<Predicate> predicates = new ArrayList<>();
		if (lastNHour != null) {
			LocalDateTime from = LocalDateTime.now();
			if (date != null) {
				from = LocalDateTime.of(date, LocalTime.now());
			}
			
			top = from;
			bottom = from.minusHours(lastNHour);
			
			Predicate previous = null;
			for (LocalDate localDate : new DateRange(bottom.toLocalDate(), top.toLocalDate()).toList()) {
				Predicate predicate = builder.equal(datePath, localDate);
				
				if (previous == null) {
					previous = predicate;
				} else {
					previous = builder.or(previous, predicate);
				}
			}
			
			predicates.add(previous);
		} else {
			top = null;
			bottom = null;
			
			if (date != null) {
				predicates.add(builder.equal(datePath, date));
			}
		}
		
		if (machine != null) {
			predicates.add(builder.equal(machinePath, machine));
		}
		
		List<BeaconExport> exports = entityManager
				.createQuery(query
						.select(root)
						.where(predicates.toArray(new Predicate[predicates.size()])))
				.getResultList();
		
		if (lastNHour != null) {
			return exports.stream().filter((export) -> {
				LocalDateTime dateTime = export.toLocalDateTime();
				
				return top.compareTo(dateTime) >= 0 && bottom.compareTo(dateTime) < 0;
			}).collect(Collectors.toList());
		}
		
		return exports;
	}
	
}