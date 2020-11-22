package jeldwen.backend.glial.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jeldwen.backend.common.model.api.impl.ApiAnwser;
import jeldwen.backend.glial.service.BeaconURLService;
import lombok.Data;

@Validated
@RestController
@RequestMapping(path = "/glial/beacon-urls", produces = MediaType.APPLICATION_JSON_VALUE)
public class BeaconURLRestController {
	
	@Autowired
	private BeaconURLService beaconURLService;
	
	@GetMapping
	public ResponseEntity<?> list() {
		return new ApiAnwser<>(beaconURLService.all()).toResponseEntity();
	}
	
	@GetMapping("machines")
	public ResponseEntity<?> machines() {
		return new ApiAnwser<>(beaconURLService.listMachines()).toResponseEntity();
	}
	
	@PostMapping
	public ResponseEntity<?> set(@RequestBody @Valid SetBody body) {
		return new ApiAnwser<>(beaconURLService.set(body.getMachine(), body.getUrl())).toResponseEntity();
	}
	
	@DeleteMapping
	public ResponseEntity<?> delete(@RequestParam String machine) {
		beaconURLService.delete(machine);
		
		return new ApiAnwser<>().toResponseEntity();
	}
	
	@Data
	private static class SetBody {
		
		@NotNull
		@NotEmpty
		@Size(max = 255)
		private String machine;
		
		@NotNull
		@NotEmpty
		@URL
		@Size(max = Short.MAX_VALUE)
		private String url;
		
	}
	
}