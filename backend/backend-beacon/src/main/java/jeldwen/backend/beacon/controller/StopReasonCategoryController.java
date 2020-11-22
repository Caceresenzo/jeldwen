package jeldwen.backend.beacon.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jeldwen.backend.beacon.dto.StopReasonCategoryUpdateBody;
import jeldwen.backend.beacon.service.IStopReasonCategoryService;
import jeldwen.backend.common.model.api.impl.ApiAnwser;

@Validated
@RestController
@RequestMapping(path = "/beacon/stop-reason/category", produces = MediaType.APPLICATION_JSON_VALUE)
public class StopReasonCategoryController {
	
	@Autowired
	private IStopReasonCategoryService stopReasonCategoryService;
	
	@GetMapping
	public ResponseEntity<?> all() {
		return new ApiAnwser<>(stopReasonCategoryService.listAll()).toResponseEntity();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> find(@PathVariable long id) {
		return new ApiAnwser<>(stopReasonCategoryService.find(id)).toResponseEntity();
	}
	
	@PutMapping
	public ResponseEntity<?> create(@Valid @RequestBody StopReasonCategoryUpdateBody body) {
		return new ApiAnwser<>(stopReasonCategoryService.create(body)).toResponseEntity();
	}
	
}