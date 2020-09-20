package jeldwen.backend.beacon.controller;

import java.util.Arrays;
import java.util.List;

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
	
	@GetMapping
	public ResponseEntity<?> newBeacon() {
		beaconRepository.deleteAll();
		stopReasonGroupRepository.deleteAll();
		stopReasonRepository.deleteAll();
		stopReasonCategoryRepository.deleteAll();
		productFamilyRepository.deleteAll();
		
		StopReasonCategory planifiedStopCategory = stopReasonCategoryRepository.save(new StopReasonCategory().setName("Arrêts planifiés").setColor("#EEC978"));
		StopReasonCategory provideFaultCategory = stopReasonCategoryRepository.save(new StopReasonCategory().setName("Défaut alimentation de la ligne").setColor("#E4A16C"));
		StopReasonCategory ligneStopCategory = stopReasonCategoryRepository.save(new StopReasonCategory().setName("Arrêt ligne").setColor("#FDBBAF"));
		StopReasonCategory serieChangeCategory = stopReasonCategoryRepository.save(new StopReasonCategory().setName("Changement de série").setColor("#8EB98C"));
		StopReasonCategory faultCategory = stopReasonCategoryRepository.save(new StopReasonCategory().setName("Panne").setColor("#7ECCD6"));
		
		List<StopReason> reasons = Arrays.asList(
				stopReasonRepository.save(new StopReason().setName("Top 5/Réunion").setCategory(planifiedStopCategory)),
				stopReasonRepository.save(new StopReason().setName("Formation").setCategory(planifiedStopCategory)),
				stopReasonRepository.save(new StopReason().setName("Pause").setCategory(planifiedStopCategory)),
				stopReasonRepository.save(new StopReason().setName("Nettoyage").setCategory(planifiedStopCategory)),
				stopReasonRepository.save(new StopReason().setName("Manque Porte").setCategory(provideFaultCategory)),
				stopReasonRepository.save(new StopReason().setName("Manque Huis").setCategory(provideFaultCategory)),
				stopReasonRepository.save(new StopReason().setName("Erreur Prépa").setCategory(provideFaultCategory)),
				stopReasonRepository.save(new StopReason().setName("Attente Mat.").setCategory(provideFaultCategory)),
				stopReasonRepository.save(new StopReason().setName("Porte NC").setCategory(provideFaultCategory)),
				stopReasonRepository.save(new StopReason().setName("Huis NC").setCategory(provideFaultCategory)),
				stopReasonRepository.save(new StopReason().setName("Erreur op.").setCategory(ligneStopCategory)),
				stopReasonRepository.save(new StopReason().setName("Evacuation").setCategory(ligneStopCategory)),
				stopReasonRepository.save(new StopReason().setName("Attente op.").setCategory(ligneStopCategory)),
				stopReasonRepository.save(new StopReason().setName("Pb film plastiq.").setCategory(ligneStopCategory)),
				stopReasonRepository.save(new StopReason().setName("PB cassé").setCategory(ligneStopCategory)),
				stopReasonRepository.save(new StopReason().setName("Attente four").setCategory(ligneStopCategory)),
				stopReasonRepository.save(new StopReason().setName("MEP").setCategory(serieChangeCategory)),
				stopReasonRepository.save(new StopReason().setName("Réglages").setCategory(serieChangeCategory)),
				stopReasonRepository.save(new StopReason().setName("Prépa bloc").setCategory(serieChangeCategory)),
				stopReasonRepository.save(new StopReason().setName("Prépa écharp.").setCategory(serieChangeCategory)),
				stopReasonRepository.save(new StopReason().setName("Prépa emb.").setCategory(serieChangeCategory)),
				stopReasonRepository.save(new StopReason().setName("Chamgm film").setCategory(serieChangeCategory)),
				stopReasonRepository.save(new StopReason().setName("Déplieur").setCategory(faultCategory)),
				stopReasonRepository.save(new StopReason().setName("Cadrage").setCategory(faultCategory)),
				stopReasonRepository.save(new StopReason().setName("Emballeuse").setCategory(faultCategory)),
				stopReasonRepository.save(new StopReason().setName("Four").setCategory(faultCategory)),
				stopReasonRepository.save(new StopReason().setName("Empileur").setCategory(faultCategory)),
				stopReasonRepository.save(new StopReason().setName("Transferts").setCategory(faultCategory)));
		
		ProductFamily family1 = productFamilyRepository.save(new ProductFamily().setName("grass").setCycleTime(10).setKey("xxx"));
		ProductFamily family2 = productFamilyRepository.save(new ProductFamily().setName("dirt").setCycleTime(15).setKey("yyy"));
		ProductFamily family3 = productFamilyRepository.save(new ProductFamily().setName("stone").setCycleTime(25).setKey("zzz"));
		ProductFamily family4 = productFamilyRepository.save(new ProductFamily().setName("mud").setCycleTime(60).setKey("aaa"));
		
		StopReasonGroup group = stopReasonGroupRepository.save(new StopReasonGroup().setName("the group")
				.setChildren(reasons));
		
		return new ApiAnwser<>(beaconRepository.save(new Beacon()
				.setName("test2")
				.setProductFamilies(Arrays.asList(family1, family2, family3, family4))
				.setStopReasonGroups(Arrays.asList(group)))).toResponseEntity();
	}
	
}