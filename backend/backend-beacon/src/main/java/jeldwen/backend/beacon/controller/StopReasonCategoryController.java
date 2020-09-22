package jeldwen.backend.beacon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jeldwen.backend.beacon.service.IStopReasonGroupService;
import jeldwen.backend.common.model.api.impl.ApiAnwser;

@RestController
@RequestMapping(path = "/beacon/stop-reason/category", produces = MediaType.APPLICATION_JSON_VALUE)
public class StopReasonCategoryController {
	
	@Autowired
	private IStopReasonGroupService stopReasonGroupService;
	
	@GetMapping
	public ResponseEntity<?> all() {
		return new ApiAnwser<>(stopReasonGroupService.listAll()).toResponseEntity();
	}
	
}