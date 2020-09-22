package jeldwen.backend.beacon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jeldwen.backend.beacon.service.IStopReasonService;
import jeldwen.backend.common.model.api.impl.ApiAnwser;

@RestController
@RequestMapping(path = "/beacon/stop-reason", produces = MediaType.APPLICATION_JSON_VALUE)
public class StopReasonController {
	
	@Autowired
	private IStopReasonService stopReasonService;
	
	@GetMapping
	public ResponseEntity<?> all(@RequestParam(required = false, defaultValue = "false") boolean ignoreGroup) {
		return new ApiAnwser<>(stopReasonService.listAll(ignoreGroup)).toResponseEntity();
	}
	
}