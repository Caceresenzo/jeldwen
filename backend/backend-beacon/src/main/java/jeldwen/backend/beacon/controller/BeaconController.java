package jeldwen.backend.beacon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jeldwen.backend.beacon.service.IBeaconService;
import jeldwen.backend.beacon.service.IStopReasonGroupService;
import jeldwen.backend.beacon.service.IStopReasonService;
import jeldwen.backend.common.model.api.impl.ApiAnwser;

@RestController
@RequestMapping(path = "/beacon", produces = MediaType.APPLICATION_JSON_VALUE)
public class BeaconController {
	
	@Autowired
	private IBeaconService beaconService;
	
	@Autowired
	private IStopReasonService stopReasonService;
	
	@Autowired
	private IStopReasonGroupService stopReasonGroupService;
	
	@GetMapping
	public ResponseEntity<?> all() {
		return new ApiAnwser<>(beaconService.listAll()).toResponseEntity();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> find(@PathVariable long id) {
		return new ApiAnwser<>(beaconService.find(id)).toResponseEntity();
	}
	
	@GetMapping("{id}/stop-reason")
	public ResponseEntity<?> stopReasons(@PathVariable long id) {
		return new ApiAnwser<>(stopReasonService.list(id, false)).toResponseEntity();
	}
	
	@GetMapping("{id}/stop-reason/all")
	public ResponseEntity<?> allStopReasons(@PathVariable long id) {
		return new ApiAnwser<>(stopReasonService.list(id, true)).toResponseEntity();
	}
	
	@GetMapping("{id}/stop-reason-group")
	public ResponseEntity<?> stopReasonGroups(@PathVariable long id) {
		return new ApiAnwser<>(stopReasonGroupService.list(id)).toResponseEntity();
	}
	
}