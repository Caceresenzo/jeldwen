package jeldwen.backend.beacon.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jeldwen.backend.beacon.dto.StopReasonUpdateBody;
import jeldwen.backend.beacon.service.IStopReasonService;
import jeldwen.backend.beacon.service.IStopReasonService.Inclusion;
import jeldwen.backend.common.model.api.impl.ApiAnwser;

@Validated
@RestController
@RequestMapping(path = "/beacon/stop-reason", produces = MediaType.APPLICATION_JSON_VALUE)
public class StopReasonController {
	
	@Autowired
	private IStopReasonService stopReasonService;
	
	@GetMapping
	public ResponseEntity<?> all(@RequestParam(required = false, defaultValue = "true") boolean includeFree, @RequestParam(required = false) Inclusion includeParent, @RequestParam(required = false) Long parentId, @RequestParam(required = false) boolean ignoreParent) {
		return new ApiAnwser<>(stopReasonService.listAll(includeFree, includeParent, parentId, ignoreParent)).toResponseEntity();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> find(@PathVariable long id) {
		return new ApiAnwser<>(stopReasonService.find(id)).toResponseEntity();
	}
	
	@PutMapping
	public ResponseEntity<?> create(@Valid @RequestBody StopReasonUpdateBody body) {
		return new ApiAnwser<>(stopReasonService.create(body)).toResponseEntity();
	}
	
	@PostMapping("{id}")
	public ResponseEntity<?> update(@PathVariable long id, @Valid @RequestBody StopReasonUpdateBody body) {
		return new ApiAnwser<>(stopReasonService.update(id, body)).toResponseEntity();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		return new ApiAnwser<>(stopReasonService.delete(id)).toResponseEntity();
	}
	
	@PostMapping("{id}/detach")
	public ResponseEntity<?> detach(@PathVariable long id) {
		return new ApiAnwser<>(stopReasonService.ungroup(id)).toResponseEntity();
	}
	
}