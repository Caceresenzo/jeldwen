package jeldwen.backend.glial.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jeldwen.backend.common.model.api.impl.ApiAnwser;
import jeldwen.backend.glial.http.MediaTypes;
import jeldwen.backend.glial.service.BeaconExportService;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiRestController {
	
	@Autowired
	private BeaconExportService beaconExportService;
	
	@GetMapping("machines")
	public ResponseEntity<?> machines() {
		return new ApiAnwser<>(beaconExportService.listMachines()).toResponseEntity();
	}
	
	@GetMapping("dates/{machine}")
	public ResponseEntity<?> dates(@PathVariable String machine) {
		return new ApiAnwser<>(beaconExportService.listDates(machine)).toResponseEntity();
	}
	
	@GetMapping("entries")
	public ResponseEntity<?> list(@RequestParam(required = false) LocalDate date) {
		return new ApiAnwser<>(beaconExportService.list(date)).toResponseEntity();
	}
	
	@GetMapping("entries/{machine}")
	public ResponseEntity<?> list(@PathVariable String machine, @RequestParam(required = false) LocalDate date) {
		return new ApiAnwser<>(beaconExportService.list(date, machine)).toResponseEntity();
	}
	
	@GetMapping(path = "download", produces = MediaTypes.TEXT_TEXT_VALUE)
	@ResponseBody
	public String download(@RequestParam(required = false) LocalDate date) {
		return beaconExportService.csv(date);
	}
	
	@GetMapping(path = "download/{machine}", produces = MediaTypes.TEXT_TEXT_VALUE)
	@ResponseBody
	public String download(@PathVariable String machine, @RequestParam(required = false) LocalDate date) {
		return beaconExportService.csv(date, machine);
	}
	
}