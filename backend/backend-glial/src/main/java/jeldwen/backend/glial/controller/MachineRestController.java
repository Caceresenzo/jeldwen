package jeldwen.backend.glial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jeldwen.backend.common.model.api.impl.ApiAnwser;
import jeldwen.backend.glial.service.BeaconExportService;

@RestController
@RequestMapping(path = "/glial/machines", produces = MediaType.APPLICATION_JSON_VALUE)
public class MachineRestController {
	
	@Autowired
	private BeaconExportService beaconExportService;
	
	@GetMapping
	public ResponseEntity<?> list() {
		return new ApiAnwser<>(beaconExportService.listMachines()).toResponseEntity();
	}
	
}