package jeldwen.backend.beacon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jeldwen.backend.beacon.dto.DetectorUpdateBody;
import jeldwen.backend.beacon.entity.Detector;
import jeldwen.backend.beacon.repository.BeaconRepository;
import jeldwen.backend.beacon.repository.DetectorRepository;
import jeldwen.backend.beacon.service.IBeaconClientService;
import jeldwen.beacon.message.model.IBeaconMessage;
import jeldwen.beacon.message.model.request.impl.auth.AuthRequestMessage;

@Service
public class BeaconClientServiceImpl implements IBeaconClientService {
	
	@Autowired
	private BeaconRepository beaconRepository;
	
	@Autowired
	private DetectorRepository beaconClientRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	/* Variables */
	private Map<String, Class<? extends IBeaconMessage>> messageClasses;
	
	/* Constructor */
	public BeaconClientServiceImpl() {
		this.messageClasses = new HashMap<>();
	}
	
	@PostConstruct
	private void initialize() {
		messageClasses.put(AuthRequestMessage.NAME, AuthRequestMessage.class);
	}
	
	@Override
	public List<Detector> listAll() {
		return beaconClientRepository.findAll();
	}
	
	@Override
	public Detector find(long id) {
		return beaconClientRepository.findById(id).orElse(null);
	}
	
	@Override
	public Detector update(long id, DetectorUpdateBody body) {
		Optional<Detector> optional = beaconClientRepository.findById(id);
		
		if (optional.isPresent()) {
			Detector beaconClient = optional.get();
			
			return beaconClientRepository.save(beaconClient
					.setName(body.getName())
					.setBeacon(beaconRepository.findById(body.getBeacon()).orElse(null)));
		}
		
		return null;
	}
	
	@Override
	public IBeaconMessage parseMessage(String json) throws Exception {
		Map<String, Object> map = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
		});
		
		return objectMapper.convertValue(map, messageClasses.getOrDefault(map.get("name"), AuthRequestMessage.class));
	}
	
	@Override
	public String stringifyMessage(IBeaconMessage message) throws Exception {
		return objectMapper.writeValueAsString(message);
	}
	
	@Override
	public Detector createIfNotExistsClient(AuthRequestMessage message) {
		Detector beaconClient = beaconClientRepository.findByUnique(message.getUnique()).orElse(null);
		
		if (beaconClient == null) {
			beaconClient = new Detector().setUnique(message.getUnique());
		}
		
		return beaconClientRepository.save(beaconClient.incrementConnectCount());
	}
	
}