package jeldwen.backend.beacon.repository.custom.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.backend.beacon.entity.ReportedStopReason;
import jeldwen.backend.beacon.repository.custom.ReportedStopReasonRepositoryCustom;

@Repository
public class ReportedStopReasonRepositoryCustomImpl implements ReportedStopReasonRepositoryCustom {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<ReportedStopReason> search(Beacon beacon, LocalDate startDate, LocalDate endDate) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<ReportedStopReason> query = builder.createQuery(ReportedStopReason.class);
		Root<ReportedStopReason> root = query.from(ReportedStopReason.class);
		
		List<Predicate> predicates = new ArrayList<>();
		
		Path<LocalDateTime> atPath = root.get("at");
		
		if (startDate != null) {
			predicates.add(builder.greaterThanOrEqualTo(atPath, LocalDateTime.of(startDate, LocalTime.MIN)));
		}
		
		if (endDate != null) {
			predicates.add(builder.lessThanOrEqualTo(atPath, LocalDateTime.of(endDate, LocalTime.MAX)));
		}
		
		if (beacon != null) {
			predicates.add(builder.equal(root.get("beacon"), beacon));
		}
		
		return entityManager
				.createQuery(query
						.select(root)
						.where(predicates.toArray(new Predicate[predicates.size()])))
				.getResultList();
	}
	
}