package jeldwen.backend.glial.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jeldwen.backend.common.model.api.impl.ApiAnwser;
import jeldwen.backend.glial.service.BeaconExportService;

@RestController
@RequestMapping(path = "/glial/entries", produces = MediaType.APPLICATION_JSON_VALUE)
public class EntriesRestController {
	
	@Autowired
	private BeaconExportService beaconExportService;
	
	@GetMapping
	public ResponseEntity<?> list(@RequestParam(required = false) LocalDate date, @RequestParam(required = false) Long lastNHour) {
		return new ApiAnwser<>(beaconExportService.list(date, lastNHour)).toResponseEntity();
	}
	
	@GetMapping("{machine}")
	public ResponseEntity<?> list(@PathVariable String machine, @RequestParam(required = false) LocalDate date, @RequestParam(required = false) Long lastNHour) {
		return new ApiAnwser<>(beaconExportService.list(date, lastNHour, machine)).toResponseEntity();
	}
	
}