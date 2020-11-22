package jeldwen.backend.beacon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jeldwen.backend.beacon.dto.ReportedStopReasonSearchBody;
import jeldwen.backend.beacon.service.IReportedStopReasonService;
import jeldwen.backend.common.model.api.impl.ApiAnwser;

@RestController
@RequestMapping(path = "/beacon", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportedStopReasonController {
	
	@Autowired
	private IReportedStopReasonService reportedStopReasonService;
	
	@GetMapping("/stop-reason/reported")
	public ResponseEntity<?> all() {
		return new ApiAnwser<>(reportedStopReasonService.search(null, ReportedStopReasonSearchBody.EMPTY)).toResponseEntity();
	}
	
	@PostMapping("/stop-reason/reported")
	public ResponseEntity<?> all(@RequestBody ReportedStopReasonSearchBody body) {
		return new ApiAnwser<>(reportedStopReasonService.search(null, body)).toResponseEntity();
	}
	
	@GetMapping("{id}/stop-reason/reported")
	public ResponseEntity<?> byBeacon(@PathVariable long id) {
		return new ApiAnwser<>(reportedStopReasonService.search(id, ReportedStopReasonSearchBody.EMPTY)).toResponseEntity();
	}
	
	@PostMapping("{id}/stop-reason/reported")
	public ResponseEntity<?> byBeacon(@PathVariable long id, @RequestBody ReportedStopReasonSearchBody body) {
		return new ApiAnwser<>(reportedStopReasonService.search(id, body)).toResponseEntity();
	}
	
}