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

import jeldwen.backend.beacon.dto.BeaconUpdateBody;
import jeldwen.backend.beacon.service.IBeaconService;
import jeldwen.backend.common.model.api.impl.ApiAnwser;

@RestController
@RequestMapping(path = "/beacon", produces = MediaType.APPLICATION_JSON_VALUE)
public class BeaconController {
	
	@Autowired
	private IBeaconService beaconService;
	
	@GetMapping
	public ResponseEntity<?> all() {
		return new ApiAnwser<>(beaconService.listAll()).toResponseEntity();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> find(@PathVariable long id) {
		return new ApiAnwser<>(beaconService.find(id)).toResponseEntity();
	}
	
	@PostMapping("{id}")
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody BeaconUpdateBody body) {
		return new ApiAnwser<>(beaconService.update(id, body)).toResponseEntity();
	}
	
	@PostMapping("{id}/reconfigure")
	public ResponseEntity<?> reconfigure(@PathVariable long id) {
		return new ApiAnwser<>(beaconService.reconfigure(id)).toResponseEntity();
	}
	
	@PostMapping("{id}/force-trigger")
	public ResponseEntity<?> forceTrigger(@PathVariable long id) {
		return new ApiAnwser<>(beaconService.forceTrigger(id)).toResponseEntity();
	}
	
}