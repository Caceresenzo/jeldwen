package jeldwen.backend.core.controller;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CoreController {
	
	/* Variables */
	@Autowired()
	private RequestMappingHandlerMapping handlerMapping;
	
	@GetMapping("**")
	public ResponseEntity<?> form4Index(HttpServletRequest request) {
		return ResponseEntity.ok().body(IntStream.of(1).mapToObj((version) -> Collections.singletonMap("v" + version, handlerMapping.getHandlerMethods()
				.keySet()
				.stream()
				.map((info) -> info.getPatternsCondition().getPatterns().iterator().next())
				.sorted()
				.collect(Collectors.toList()))).collect(Collectors.toList()));
	}
	
}