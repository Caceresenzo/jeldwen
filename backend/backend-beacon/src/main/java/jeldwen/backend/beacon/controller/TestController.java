package jeldwen.backend.beacon.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jeldwen.backend.beacon.entity.Beacon;
import jeldwen.backend.beacon.entity.ProductFamily;
import jeldwen.backend.beacon.entity.StopReason;
import jeldwen.backend.beacon.entity.StopReasonCategory;
import jeldwen.backend.beacon.entity.StopReasonGroup;
import jeldwen.backend.beacon.repository.BeaconRepository;
import jeldwen.backend.beacon.repository.ProductFamilyRepository;
import jeldwen.backend.beacon.repository.StopReasonCategoryRepository;
import jeldwen.backend.beacon.repository.StopReasonGroupRepository;
import jeldwen.backend.beacon.repository.StopReasonRepository;
import jeldwen.backend.common.model.api.impl.ApiAnwser;

@RestController
@RequestMapping(path = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
	
	@Autowired
	private BeaconRepository beaconRepository;
	
	@Autowired
	private StopReasonRepository stopReasonRepository;
	
	@Autowired
	private StopReasonGroupRepository stopReasonGroupRepository;
	
	@Autowired
	private StopReasonCategoryRepository stopReasonCategoryRepository;
	
	@Autowired
	private ProductFamilyRepository productFamilyRepository;
	
	@GetMapping("new")
	public ResponseEntity<?> newBeacon() {
		cleanup();
		
		List<StopReason> reasons = new ArrayList<>();
		
		createCategoryAndAttachReasons(reasons, "Arrêts planifiés", "#EEC978", "Top 5/Réunion", "Formation", "Pause", "Nettoyage");
		createCategoryAndAttachReasons(reasons, "Défaut alimentation de la ligne", "#FDBBAF", "Manque Porte", "Manque Huis", "Erreur Prépa", "Attente Mat.", "Porte NC", "Huis NC");
		createCategoryAndAttachReasons(reasons, "Arrêt ligne", "#FDBBAF", "Erreur op.", "Evacuation", "Attente op.", "Pb film plastiq.", "PB cassé", "Attente four");
		createCategoryAndAttachReasons(reasons, "Changement de série", "#8EB98C", "MEP", "Réglages", "Prépa bloc", "Prépa écharp.", "Prépa emb.", "Chamgm film");
		createCategoryAndAttachReasons(reasons, "Panne", "#7ECCD6", "Déplieur", "Cadrage", "Emballeuse", "Four", "Empileur", "Transferts");
		
		Set<StopReason> other = new HashSet<>();
		createCategoryAndAttachReasons(reasons, "Specialized", "#FFFFFF", "A", "B", "C");
		
		List<ProductFamily> families = Arrays.asList(productFamilyRepository.save(new ProductFamily().setName("grass").setCycleTime(10)),
				productFamilyRepository.save(new ProductFamily().setName("dirt").setCycleTime(15)),
				productFamilyRepository.save(new ProductFamily().setName("stone").setCycleTime(25)),
				productFamilyRepository.save(new ProductFamily().setName("mud").setCycleTime(60)));
		
		StopReasonGroup group = stopReasonGroupRepository.save(new StopReasonGroup()
				.setName("the group")
				.setChildren(new HashSet<>(reasons)));
		
		reasons.forEach((stopReason) -> stopReasonRepository.save(stopReason.setGroup(group)));
		
		return new ApiAnwser<>(beaconRepository.save(new Beacon()
				.setName("Plieuse")
				.setUnique("80-FA-5B-80-EE-46")
				.setProductFamilies(families)
				.setStopReasons(other)
				.setStopReasonGroups(Arrays.asList(group)))).toResponseEntity();
	}
	
	@GetMapping("clear")
	public ResponseEntity<?> clear() {
		cleanup();
		
		return new ApiAnwser<>().toResponseEntity();
	}
	
	private void cleanup() {
		beaconRepository.findAll().forEach((beacon) -> beaconRepository.save(beacon.setStopReasons(Collections.emptySet())));
		stopReasonGroupRepository.findAll().forEach((stopReasonGroup) -> stopReasonGroupRepository.save(stopReasonGroup.setChildren(Collections.emptySet())));
		stopReasonRepository.findAll().forEach((stopReason) -> stopReasonRepository.save(stopReason.setGroup(null).setBeacon(null)));

		beaconRepository.deleteAll();
		stopReasonGroupRepository.deleteAll();
		stopReasonRepository.deleteAll();
		
		stopReasonCategoryRepository.deleteAll();
		productFamilyRepository.deleteAll();
	}
	
	private void createCategoryAndAttachReasons(List<StopReason> out, String name, String color, String... reasons) {
		StopReasonCategory category = stopReasonCategoryRepository.save(new StopReasonCategory().setName(name).setColor(color));
		
		for (String reason : reasons) {
			out.add(stopReasonRepository.save(new StopReason().setName(reason).setCategory(category)));
		}
	}
	
}