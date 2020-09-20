package jeldwen.backend.beacon.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jeldwen.backend.beacon.dto.DetectorUpdateBody;
import jeldwen.backend.beacon.service.IBeaconClientService;
import jeldwen.backend.common.model.api.impl.ApiAnwser;

@Validated
@RestController
@RequestMapping(path = "/beacon/detector", produces = MediaType.APPLICATION_JSON_VALUE)
public class DetectorController {
	
	@Autowired
	private IBeaconClientService beaconClientService;
	
	@GetMapping
	public ResponseEntity<?> all() {
		return new ApiAnwser<>(beaconClientService.listAll()).toResponseEntity();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> find(@PathVariable long id) {
		return new ApiAnwser<>(beaconClientService.find(id)).toResponseEntity();
	}
	
	@PostMapping("{id}")
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody @Valid DetectorUpdateBody body) {
		return new ApiAnwser<>(beaconClientService.update(id, body)).toResponseEntity();
	}
	
}