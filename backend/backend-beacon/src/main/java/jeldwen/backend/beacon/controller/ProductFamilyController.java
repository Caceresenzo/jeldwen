package jeldwen.backend.beacon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jeldwen.backend.beacon.service.IProductFamilyService;
import jeldwen.backend.common.model.api.impl.ApiAnwser;

@RestController
@RequestMapping(path = "/beacon/product-family", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductFamilyController {
	
	@Autowired
	private IProductFamilyService productFamilyService;
	
	@GetMapping
	public ResponseEntity<?> all() {
		return new ApiAnwser<>(productFamilyService.listAll()).toResponseEntity();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> find(@PathVariable long id) {
		return new ApiAnwser<>(productFamilyService.find(id)).toResponseEntity();
	}
	
}