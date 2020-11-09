package jeldwen.backend.glial.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jeldwen.backend.common.http.MediaTypes;
import jeldwen.backend.glial.service.BeaconExportService;

@Controller
@RequestMapping(path = "/glial/download", produces = MediaTypes.TEXT_CSV_VALUE)
public class DownloadCsvController {
	
	@Autowired
	private BeaconExportService beaconExportService;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<String> download(@RequestParam(required = false) LocalDate date) {
		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename=entries.csv")
				.body(beaconExportService.csv(date));
	}
	
	@GetMapping("{machine}")
	@ResponseBody
	public ResponseEntity<String> download(@PathVariable String machine, @RequestParam(required = false) LocalDate date) {
		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename=entries-" + machine + (date != null ? "-" + date : "") + ".csv")
				.body(beaconExportService.csv(date, machine));
	}
	
}