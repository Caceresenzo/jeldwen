package jeldwen.backend.glial.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jeldwen.backend.common.model.api.impl.ApiAnwser;
import jeldwen.backend.glial.service.BeaconExportService;

@Controller
@RequestMapping(path = "/glial/delete", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeleteRestController {
	
	@Autowired
	private BeaconExportService beaconExportService;
	
	@PostMapping
	public ResponseEntity<?> preview(@RequestBody @Valid List<LocalDate> dates, @RequestParam boolean real) {
		if (real) {
			beaconExportService.delete(dates);

			return new ApiAnwser<>().toResponseEntity();
		}
		
		return new ApiAnwser<>(beaconExportService.previewDelete(dates)).toResponseEntity();
	}
	
	@PostMapping("{machine}")
	public ResponseEntity<?> preview(@PathVariable String machine, @RequestBody @Valid List<LocalDate> dates, @RequestParam boolean real) {
		if (real) {
			beaconExportService.delete(dates, machine);

			return new ApiAnwser<>().toResponseEntity();
		}
		
		return new ApiAnwser<>(beaconExportService.previewDelete(dates, machine)).toResponseEntity();
	}
	
}