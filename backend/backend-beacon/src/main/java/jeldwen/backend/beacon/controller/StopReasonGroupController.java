package jeldwen.backend.beacon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jeldwen.backend.beacon.service.IStopReasonGroupService;
import jeldwen.backend.common.model.api.impl.ApiAnwser;

@RestController
@RequestMapping(path = "/beacon/stop-reason/group", produces = MediaType.APPLICATION_JSON_VALUE)
public class StopReasonGroupController {
	
	@Autowired
	private IStopReasonGroupService stopReasonGroupService;
	
	@GetMapping
	public ResponseEntity<?> all() {
		return new ApiAnwser<>(stopReasonGroupService.listAll()).toResponseEntity();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> find(@PathVariable long id) {
		return new ApiAnwser<>(stopReasonGroupService.listReasons(id)).toResponseEntity();
	}
	
}